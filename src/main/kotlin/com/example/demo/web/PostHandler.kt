package com.example.demo.web

import com.example.demo.Post
import com.example.demo.PostRepository
import org.springframework.http.MediaType.APPLICATION_STREAM_JSON
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI
import java.time.Duration

class PostHandler(val posts: PostRepository) {


    fun allView(req: ServerRequest): Mono<ServerResponse> {
        return ok().render("index", mapOf("posts" to this.posts.findAll().collectList().block()))
    }

    fun all(req: ServerRequest): Mono<ServerResponse> {
        return ok().body(this.posts.findAll(), Post::class.java)
    }

    fun stream(req: ServerRequest): Mono<ServerResponse> {
        return ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(
                        Flux.interval(Duration.ofSeconds(1))
                                .flatMap { this.posts.findAll() },
                        Post::class.java
                )
    }

    fun sse(req: ServerRequest): Mono<ServerResponse> {
        val postStream = Flux
                .zip(Flux.interval(Duration.ofSeconds(1)), this.posts.findAll().repeat())
                .map { it.t2 }
        return ok().bodyToServerSentEvents(postStream)
    }

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(Post::class.java)
                .flatMap { post -> this.posts.save(post) }
                .flatMap { (id) -> created(URI.create("/posts/" + id)).build() }
    }

    fun get(req: ServerRequest): Mono<ServerResponse> {
        return this.posts.findById(req.pathVariable("id"))
                .flatMap { post -> ok().body(Mono.just(post), Post::class.java) }
                .switchIfEmpty(notFound().build())
    }

    fun update(req: ServerRequest): Mono<ServerResponse> {
        return this.posts.findById(req.pathVariable("id"))
                .zipWith(req.bodyToMono(Post::class.java))
                .map { it.t1.copy(title = it.t2.title, content = it.t2.content) }
                .flatMap { this.posts.save(it) }
                .flatMap { noContent().build() }
    }

    fun delete(req: ServerRequest): Mono<ServerResponse> {
        return this.posts.deleteById(req.pathVariable("id"))
                .flatMap { noContent().build() }
    }
}


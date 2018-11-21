>Webflux, Kotlin, Mongo, Docker, Junit

Данный проект демонстрирует построение простого веб-приложения, построенного с помощью 
`Spring 5` в реактивном стиле, и написанного на языке Kotlin.

Включены следующие функции:

* [Поддержку фрэймворком `Spring` 5 языка Котлина](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0);
* [Gradle Kotlin DSL](https://github.com/gradle/kotlin-dsl)
* Null-safety Spring, Reactor API;
* `WebFlux Reactive` веб-сервер и клиент;
* `Mustache`-шаблон для веб-просмотра;
* Функциональные `RESTful API` с `Spring Webflux` спецификой `RouterFunction`;
* `Spring Security Reactive`;
* Kotlin предпочел  `BeanDefinitionDSL` для объявления  бинов в стиле DSL;
* [`Junit 5` `@BeforeAll` и `@AfterAll`](https://github.com/sdeleuze/spring-kotlin-functional/blob/master/src/test/kotlin/functional/IntegrationTests.kt) 
на нестатических методах.


> [Тут другой пример (офиц. сайт)](https://github.com/hantsy/spring-reactive-sample)

### Запуск из IDE:

1. Скачайте проект, откройте его в Intellij Idea.

```
git clone https://github.com/steklopod/Kotlin-with-WebFlux.git
```

2. Запустите `Mongodb`. `docker-compose.yml` находится в корневой папке.

```
docker-compose up mongodb
```

3. Запустите `Application.kt` в IDE напрямую. 


### Запуск из консоли:

   * Зайдите в корень проекта
   * Выполните`gradle build` чтобы собрать проект
   * После сборки вы обнаружите `XXX-all.jar` в папке *build/lib* 
   * Выполните `java -jar XXX-all.jar` чтобы стартовать приложение

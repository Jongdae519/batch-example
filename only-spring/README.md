## Java 21
Oracle OpenJDK 21.0.2 사용

## Gradle 8
gradle 8.8 사용

## Build
Gradle 탭에서 `only-spring > Tasks > build > clean` + `only-spring > Tasks > other > buildJar`

또는

Terminal에서 Command Prompt를 열고 only-spring project의 폴더에서 `gradle clean buildJar` 사용

또는

~~\batch-example\only-spring> 경로에서
```shell
gradlew clean buildJar
```

## Run exampleJob
~~\batch-example\only-spring> 경로에서

### Command Prompt
```
java -Dspring.profiles.active=dev -jar build/libs/only-spring.jar com.example.ExampleBatchConfiguration exampleJob id=1
```

### Powershell
```shell
java `-Dspring.profiles.active=dev -jar build/libs/only-spring.jar com.example.ExampleBatchConfiguration exampleJob id=1
```
> Powershell에서 버그가 있어 profile을 설정하는 command 앞에 `를 붙여서 해결
>
> Powershell에서 `echo -Hello.world` 사용 시 -Hello, .world 2줄로 나오면 버그 있는 것
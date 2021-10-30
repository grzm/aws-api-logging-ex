# aws-api logging suppression
m
The aws-api is wonderful but kinda noisy upon initial invocations for
a client. Here's an example of how to suppress the "Unable to fetch
credentials" messages by setting the java.util.logging level to
WARNING.

## To run

Noisy (default)

```
clj -X com.grzm.ex.pod-aws/ex
2021-10-30 10:38:02.623:INFO::main: Logging initialized @5482ms to org.eclipse.jetty.util.log.StdErrLog
Oct 30, 2021 10:38:02 AM clojure.tools.logging$eval9763$fn__9766 invoke
INFO: Unable to fetch credentials from environment variables.
Oct 30, 2021 10:38:02 AM clojure.tools.logging$eval9763$fn__9766 invoke
INFO: Unable to fetch credentials from system properties.
(:Buckets :Owner)
(:UserId :Account :Arn)
```

### A solution

The aws-api client uses clojure.tools.logging, and falls back to
java.util.logging as there is no other logging implementation.

https://github.com/clojure/tools.logging/blob/2472b6f84853075cb58082753ec39f49d1716dc2/src/main/clojure/clojure/tools/logging/impl.clj#L245-L256

The secret sauce is to specify the java.util.logging config file using
a jvm parameter `-Djava.util.logging.config.file=logging.properties`
which specifies the config logging level.

### Quiet

```
clj -A:quiet -X com.grzm.ex.pod-aws/ex
2021-10-30 10:48:31.842:INFO::main: Logging initialized @5090ms to org.eclipse.jetty.util.log.StdErrLog
(:Buckets :Owner)
(:UserId :Account :Arn)
```

See? Isn't that great? ... Wait. Seriously? What's that `Logging initialized`
line still doing there? _*I thought I told you to be quiet?!*_

### REALLY Quiet!

```
clj -A:REALLY:quiet -X com.grzm.ex.pod-aws/ex
(:Buckets :Owner)
(:UserId :Account :Arn)
```

To get rid of the "Logging initialized, have
`jetty-logging.properties` on the class path with
`org.eclipse.jetty.util.log.announce = false`.

Crikey.

See [deps.edn](./deps.edn) for details.

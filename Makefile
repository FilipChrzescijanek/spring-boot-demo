local:
	# start local Spring Boot demo app
	./mvnw clean spring-boot:run

tests:
	# run integration tests
	./mvnw clean test

docker:
    # build application image
	 ./mvnw spring-boot:build-image

deploy:
	# run Docker container
	docker run -d --name spring-boot-demo-app -it -p8080:8080 spring-boot-demo:0.0.1-SNAPSHOT

stop:
	# stop Docker container
	docker rm -f spring-boot-demo-app || true

redeploy: stop deploy

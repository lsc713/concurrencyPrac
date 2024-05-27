TAG                        := $$(git log -1 --pretty=format:%h)
CONTAINER_NAME             := devcamp-example-backend
IMG_COMMIT_SERVER     		:= ${CONTAINER_NAME}-server-$$env:${TAG}
IMG_LATEST_SERVER     		:= ${CONTAINER_NAME}-server-$$env:latest

.build:
	@docker buildx build -f docker/Dockerfile --build-arg PROFILE=$$env -t ${IMG_COMMIT_SERVER} .
	@docker tag ${IMG_COMMIT_SERVER} ${IMG_LATEST_SERVER}

.run:
	@sh docker/$$env.sh

.kill:
	@echo 'Killing container...'
	@docker-compose -f docker/$$env/docker-compose-$$env.yaml -p ${CONTAINER_NAME}-server-$$env down
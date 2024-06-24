# Item-Management-System

An item management system that mainly focus on books (other general items is also supported). This is the team project of COMP30022 at University of Melbourne. This project will be redeployed in the future.

## Read them before getting started

- [Frontend Readme](./FRONTEND_README.md)
- [Backend Readme](./BACKEND_README.md)

## Code Styles

For backend (most likely Java):
[Alibaba Java Coding Guidelines](https://github.com/alibaba/Alibaba-Java-Coding-Guidelines)

For Frontend (most likely TypeScript):
[Google TypeScript Style Guide](https://google.github.io/styleguide/tsguide.html)

Code styles will be enforced by linters, which is, if style isn't correct, the code won't get compiled :)

### Linters

Okay, it might be necessary to explain how linters contribute to code styles :)

So, the project is build by nodeJS and gradle for frontend and backend, so during **every** build, a special program called linter will be run, before it actually builds anything. Linter will scan on **every** code file, to see if there is anything break the style! And if it found any, the build will be **rejected**, so it is impossible to write invalid style codes!

Also, everytime some code is commit to the git, CI will be run, to manually called the linters to check the code, and if anything break the style, it will be notified.

Thus the code style is enforced! Cheers!

## Get started

### Frontend Development

Starting by run below in terminal:

```bash
npm run dev
```

or

```bash
./gradlew debugFrontend
```

### Backend Development

It is recommanded to import project into IntellJ and using built in spring boot development tool chains. But it can be
run inside terminals, by running command below:

```bash
./gradlew bootRun
```

### Restful API Doc

See [BackendControllerApi](./src/main/resources/static/doc/BackendControllerApi.md)

And [UserControllerApi](./src/main/resources/static/doc/UserControllerApi.md)

UserControllerApi contains most of the useful apis, so check that!

<!-- ### Build & Deployment

Build automacaticlly happened once a commit is pushed. The pre-deployed site will be updated once it is build, the link will be https://findme.apisium.cn

System can be deployed to any Docker-installed server, for more information on docker, refers to: https://docs.docker.com/engine/install/

On any docker installed system, run following commands to deploy FindMe:

```bash
docker volume create findme # Creating a volume for database
docker pull registry.apisium.cn/team12/item:latest # Update docker image
docker run -v findme:/var/lib/mysql -d -p 80:80 registry.apisium.cn/team12/item:latest # Run
``` -->

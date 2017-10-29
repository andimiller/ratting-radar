FROM nginx
COPY target/scala-2.12/scalajs-bundler/main/ratting-radar-fastopt-bundle.js /usr/share/nginx/html/
COPY target/scala-2.12/scalajs-bundler/main/ratting-radar-fastopt-bundle.js.map /usr/share/nginx/html/
COPY src/main/resources/webroot/* /usr/share/nginx/html/
EXPOSE 80
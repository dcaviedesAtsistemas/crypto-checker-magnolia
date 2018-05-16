![magnolia-logo](https://www.magnolia-cms.com/.imaging/mte/corporate-website-2017-theme/2700/dam/en/pages-app/www-en-intl/events/ES-developers-competition-2018/header_competicion_2160x1080_Eventbrite.jpg/jcr:content/header_2160x1080_Eventbrite.jpg)

# Módulo Crypto-checker y SPA (Single-page application) ReactJS

## Retos Digitales Magnolia Community 2018 [#RDMagnolia](https://www.magnolia-cms.com/events/2018-retos-digitales-magnolia-community-es) :rocket:

Este repositorio recoge un proyecto completo con la versión _Magnolia Community Edition (CE)_ y un módulo con la aplicación creada para participar en el **Reto API REST - Headless**.

## ¿Qué hace? :chart_with_upwards_trend:

Nuestra aplicación mostrará los datos de las criptomonedas seleccionadas. Los datos, de las útlimas 24H, serán controlados y servidos mediante API REST por Magnolia.

## Tecnologías :wrench:

Para el desarrollo en Magnolia hemos hecho uso Delivery endpoint API v2 para servir los datos que recogeremos con la aplicación SPA en ReactJS.

## ¿Cómo funciona? :bulb:

Nuestro módulo Magnolia `crypto-checker-magnolia` tomará datos de las cryptomonedas que se le indiquen en la
aplicación de contenido. Una vez, añadido las monedas y los datos que queremos mostrar serviremos estos datos
a través de una API REST configurada.

Para mostrar estos datos tenemos una SPA construida con ReactJS, en tiempo real, recogerá los datos de las
últimas 24H de nuestras cryptomonedas. Además podremos hacer uso de un formulario para subscribirse y recibir
así los datos vía email.

## Demo :bar_chart:

Si queremos probar el proyecto localmente debemos, en primer lugar, levantar nuestro proyecto Magnolia que servirá los datos a nuestra SPA. Este proyecto ya tiene configurada unas criptomonedas por defecto por lo que no es necesario configurar nada en Magnolia. Luego será necesario tener instalados en nuestro sistema NodeJS, NPM y Python, y entonces situados en el directorio `spa-react` desde una línea de comandos ejecutaremos

```bash
npm install
npm start
```

Luego abriremos [`localhost:8000`](http://localhost:8000) en nuestro navegador. Si vemos que no se dibujan las gráficas y en la consola del navegador muestra un fallo de CORS, instalaremos en nuestro navegador cualquier plugin para habilitar el CORS, aunque no debería ser necesario ya que está configurado un filtro en Magnolia para habilitar el CORS.
Nota: el contexto del WAR desplegado debe ser "magnolia-webapp".

## Capturas :camera:

![Tabla](https://raw.githubusercontent.com/DavidCaviedes/openexpo-app-monitoring/master/openexpo-app-monitoring/src/main/resources/img/details.png)

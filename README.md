# weather.balloon.statistics - Weather balloon traversing the globe

There is a weather balloon traversing the globe, periodically taking observations. At each observation, the balloon records the temperature and its current location. When possible, the balloon relays this data back to observation posts on the ground.

## Getting Started

* The file generator is generating quality information, so information is generated as described in requirement.
* The total distance is being calculated using the distance between two points formula but then I did not adjust the distance to any distance unit presented in the requirement, I do not understand how to apply this rule since requirement does not describe how to perform this transformation.

### Prerequisites

* Java 8
* [Maven](https://maven.apache.org/)
* [Git](https://git-scm.com/)


### Installing

Pasos a seguir...

Clonar repositorio

```
git clone https://gitlab.com/cuentastighost/scheduler.git
```

Entrar a la carpeta del cliente

```
cd scheduler/notification-system-client/

ls
pom.xml  README.md  src/
```

Limpiar el proyecto, la salida deberia de ser similar

```
$ mvn clean
[INFO] Scanning for projects...
[INFO]
[INFO] ----------------< IT-Ghost:notification-sysmtem-client >----------------
[INFO] Building notification-sysmtem-client 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ notification-sysmtem-client ---
[INFO] Deleting C:\Users\luisgonz\git\scheduler\notification-system-client\target
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.328 s
[INFO] Finished at: 2018-09-15T15:32:08-05:00
[INFO] ------------------------------------------------------------------------
```

Construir el proyecto, la salida deberia de ser similar

```
mvn install
[INFO] Scanning for projects...
[INFO]
[INFO] ----------------< IT-Ghost:notification-sysmtem-client >----------------
[INFO] Building notification-sysmtem-client 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
								...............................
								...............................
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 3.438 s
[INFO] Finished at: 2018-09-15T15:32:48-05:00
[INFO] ------------------------------------------------------------------------

```

Con estos pasamos ya tenemos el jar, el cliente instalado en nuestro repositorio de maven local, con esto podemos incluirlo en nuestro proyecto para poder
utilizar el sistema de notificaciones.

## Ejemplos para utilizar el cliente

A continuación se muestran unos snippets de codigo de como se utiliza el cliente, estos ejemplos ya usan una instacia de sistema de notificaciones en HEROKU, así que no es necesario instalar el servidor, si el cliente regresara algun error de que la URL de heroku no responde es porqué esta hosteado en una cuenta free, entonces hay qu eejecutar una o dos requests para que el servicio se active.

### Subir un template a la base de datos para que se pueda utilizar para generar contenido dinámico en HTML

```java
ITGHostNotificationClient client = new ITGHostNotificationClient("dbuser", "123",  "https://polygon-notification-system.herokuapp.com/polygon-notification/");

NotificationResponse rUpload = client.uploadTemplateFile("template_html_final_1",
				new File("C:\\ruta\\al\\archivo\\template\\emailTemaplate1.html"), false);

System.out.println("Respuesta de file upload: " + rUpload);
```

### Enviar una notificación email simple, sin formato HTML, solo texto plano

```java
ITGHostNotificationClient client = new ITGHostNotificationClient("dbuser", "123", "https://polygon-notification-system.herokuapp.com/polygon-notification/");

//declaramos unos archivos a agregar como attachments.
File[] files = {
			new File("C:\\Users\\luisgonz\\Pictures\\Capture.JPG"),
			new File("C:\\Users\\luisgonz\\Pictures\\MessageWrong.JPG"),
};

NotificationResponse rSendSimple = client.sendSimpleEmailNotification("Hola desde cliente poderoso en java [sendSimpleEmailNotification] con Attachmentes...", 
				"darklatiz@hotmail.com", 
				"darklatiz@hotmail.com,software.j2ee@gmail.com", 
				"Prueba Cliente java 1", 
				files);
		
System.out.println("Respuesta de simple email notification : " + rSendSimple);
```
Si no se quieren enviar attachments simplemente se manda un null

### Enviar una notificación con contenido HTML usando templates, el template tiene que existir enla base de datos

```java
ITGHostNotificationClient client = new ITGHostNotificationClient("dbuser", "123", "https://polygon-notification-system.herokuapp.com/polygon-notification/");

//declaramos unos archivos a agregar como attachments.
File[] files = {
			new File("C:\\Users\\luisgonz\\Pictures\\Capture.JPG"),
			new File("C:\\Users\\luisgonz\\Pictures\\MessageWrong.JPG"),
};

NotificationResponse rSendSimple = client.sendSimpleEmailNotification(List<TemplateProperty> ps = createDummyProps();
NotificationResponse rSendHTML = client.sendEmailNotificationUsingHTMLTemplate("darklatiz@hotmail.com", 
			"darklatiz@hotmail.com,software.j2ee@gmail.com", 
			"Prueba Cliente java 3 - HTML Content [sendEmailNotificationUsingHTMLTemplate] con Attachments", 
			"template_html_final_1", 
			ps, 
			files);
		
System.out.println("Respuesta de HMTL content email notification : " + rSendHTML);

private static List<TemplateProperty> createDummyProps() {
		TemplateProperty p = new TemplateProperty("nombre", "luis", "darklatiz@hotmail.com");
		TemplateProperty p1 = new TemplateProperty("fecha", "23 Agosto 1980", "darklatiz@hotmail.com");
		TemplateProperty p2 = new TemplateProperty("email", "darklatiz@hotmail.com", "darklatiz@hotmail.com");
		TemplateProperty p3 = new TemplateProperty("estatus", "red-alert", "darklatiz@hotmail.com");
		
		TemplateProperty p4 = new TemplateProperty("nombre", "pecas", "software.j2ee@gmail.com");
		TemplateProperty p5 = new TemplateProperty("fecha", "13 Septiembre 1990", "software.j2ee@gmail.com");
		TemplateProperty p6 = new TemplateProperty("email", "software.j2ee@gmail.com", "software.j2ee@gmail.com");
		TemplateProperty p7 = new TemplateProperty("estatus", "green-alert", "software.j2ee@gmail.com");
		
		List<TemplateProperty> ps = new ArrayList<>();
		ps.add(p);
		ps.add(p1);
		ps.add(p2);
		ps.add(p3);
		ps.add(p4);
		ps.add(p5);
		ps.add(p6);
		ps.add(p7);
		return ps;
	}
```
Si no se quieren enviar attachments simplemente se manda un null

### Enviar una notificación con contenido HTML, el sistema que contiene el cliente sera el encargado de generar el contenido HTML, en este caso leemos un archivo que contiene el HTML, pero este contenido puede ser generado de cualquier forma en el sistema que contiene al cliente.

```java
ITGHostNotificationClient client = new ITGHostNotificationClient("dbuser", "123", "https://polygon-notification-system.herokuapp.com/polygon-notification/");

//declaramos unos archivos a agregar como attachments.
File[] files = {
			new File("C:\\Users\\luisgonz\\Pictures\\Capture.JPG"),
			new File("C:\\Users\\luisgonz\\Pictures\\MessageWrong.JPG"),
};

FileReader htmlReadee = new FileReader("emailTemaplateCustomText1.html");
BufferedReader br = new BufferedReader(htmlReadee);
StringBuffer sb = new StringBuffer();
String line;
while ((line = br.readLine()) != null) {
         sb.append(line);
}
        
NotificationResponse rSendHTMLCustomText =client.sendEmailNotificationUsingHTMLCustomText(
       		"darklatiz@hotmail.com", //from
        		"darklatiz@hotmail.com,software.j2ee@gmail.com", //to 
        		"Prueba Cliente java 5 - HTML Content [sendEmailNotificationUsingHTMLCustomText] con attachments",//subject
        		sb.toString(),//htmlText
        		files);
        
System.out.println("Respuesta de HMTL content email notification : " + rSendHTMLCustomText);
```
Si no se quieren enviar attachments simplemente se manda un null


## Deployment

Agregar maven dependecy en el pom.xml del sistema, se muestran las dependencias que se necesitan agregar en el sistema.

```xml

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<maven.compiler.source>1.8</maven.compiler.source>
		<maven.compiler.target>1.8</maven.compiler.target>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<dependencies>
		<dependency>
			<groupId>IT-Ghost</groupId>
			<artifactId>notification-sysmtem-client</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpclient</artifactId>
			<version>4.5.6</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime -->
		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpmime</artifactId>
			<version>4.5.6</version>
		</dependency>
		<dependency>
			<groupId>org.json</groupId>
			<artifactId>json</artifactId>
			<version>20180130</version>
		</dependency>
	</dependencies>

```

###
Algunas veces hay que habilitar el LOG a nivel debug, para hacerlo es necesario pasar los siguientes parametro a la VM

```
VM arguments
-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog
-Dorg.apache.commons.logging.simplelog.showdatetime=true
-Dorg.apache.commons.logging.simplelog.log.org.apache.http=DEBUG
-Dorg.apache.commons.logging.simplelog.log.org.apache.http.wire=ERROR
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Luis Enrique Gonzalez** - *Initial work* - [PurpleBooth](https://github.com/darklatiz)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc


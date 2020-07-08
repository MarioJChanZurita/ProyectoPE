**URL de presentación de proyecto:**

[https://youtu.be/1nG8yeJDFgc](https://youtu.be/1nG8yeJDFgc)

**Miembros del equipo:**

- Alexis Ake Vela
- Diego DeGante
- Mario Chan Zurita
- Pedro Cauich Salazar

**Tabla de contenido**

1. **Introducción**

2. **Definición de proyecto**

  - 2.1 Problemática
  - 2.2 Objetivos

3. **Requerimientos**

  - 3.1 Actores del sistema
  - 3.2 Requerimientos de sistema
    - 3.2.1 Funcionales
    - 3.2.2 No funcionales
    - 3.2.3 Mapeo de requerimientos

4. **Asistente Inteligente**

  - 4.1 Definición
  - 4.2 Creación del asistente
    - 4.2.1 Agente
    - 4.2.2 Intents
    - 4.2.3 Acción
    - 4.2.4 Parámetros
    - 4.2.5 Entidades
    - 4.2.6 Respuestas
    - 4.2.7 Contextos
    - 4.2.8 Eventos
  - 4.3 Implementación del asistente
5. **Casos de uso**

  - 5.1 Diagrama de casos de uso

6. **Proceso**

  - 6.1 Estándares de codificación
  - 6.2 Proceso de desarrollo

7. **Trabajo en equipo**

  - 7.1 Roles
  - 7.2 Esquema de monitoreo
  - 7.3 Bitácoras
  - 7.4 Métrica para evaluar avance individual
  - 7.5 Avance grupal

**Sistema de alarmas relacionadas al recordatorio de recetas de medicamentos.**

**1. Introducción.**

MedicineReminer es una aplicación en la cual puedes guardar y administrar todas tus recetas médicas, permitiéndote llevar el control de tus medicamentos. Todo esto simplemente añadiendo el nombre del medicamento, la hora de la primera toma, los periodos y notas con las especificaciones médicas necesarias.

MedicineReminder llevará el control de los tiempos de ingesta de cada medicamento por ti, al igual que te mostrará todas las especificaciones necesarias al momento de tomar cada medicamento para que nunca olvides ningún detalle.

**2. Definición del proyecto.**

**2.1 Problemática:**

Al crearse el concepto de medicamentos, surge la necesidad de un consumo de estos moderado y periódico. Los medicamentos tienen el objetivo de mantener un estilo de vida a las personas, sin embargo, muchas de estas personas no son aptas para medir sus tiempos y mantener una responsabilidad óptima sobre sí mismas, es por eso que nuestro proyecto presenta una alternativa para cubrir esta necesidad de una manera eficiente.

**2.2 Objetivos:**

**Objetivo general:**

Presentar a los usuarios una forma fácil y rápida de organizar el uso de medicamentos.

**Objetivos específicos:**

A) Estructurar la información de los medicamentos de una manera eficiente y de fácil entendimiento.

B) Organizar los medicamentos en cuestión a si se encuentran activos o inactivos.

C) Notificar e indicar las especificaciones de ingesta para cada toma de cada medicamento.

D) Administrar las alarmas de acuerdo a las necesidades del usuario.

E) Automatizar el funcionamiento de las alarmas.

F) Compatible con más del 80% de dispositivos android.

G) Permitir al usuario la automatización de los procesos mediante la voz.

**3. Requerimientos**

**3.1 Actores del sistema:**

**Usuario:** persona que usa el sistema

Puede ingresar una nueva alarma de medicamento a su lista de alarmas a través de la opción de agregar alarma.

Puede editar una alarma ya creada previamente.

Puede eliminar una alarma de su lista de alarmas.

Puede agregar o eliminar una alarma haciendo uso únicamente del asistente inteligente.

Puede hacer una búsqueda en su lista de medicamentos ingresando el nombre del medicamento.

**3.2 Requerimientos de sistema**

**3.2.1 Funcionales**

| RF001 | Página principal |
| --- | --- |
| Descripción | Al iniciar la aplicación, el sistema mostrará un listado únicamente de las alarmas ya creadas por el usuario que se encuentran activas. El sistema dará la opción de administrar las alarmas. Las opciones que dará el sistema son las siguientes:
- Seleccionar una alarma de la lista
- Activar asistente inteligente
- Buscar alarma
- Agregar alarma
- Mostrar alarmas

 |

| RF002 | Seleccionar una alarma de la lista |
| --- | --- |
| Descripción | El sistema abrirá la alarma, mostrando todos los datos ingresados por el usuario. Dará la opción de editar la alarma o eliminarla.
 |

| RF003 | Eliminar alarma |
| --- | --- |
| Descripción | El sistema permitirá eliminar la alarma completamente. Teniendo en cuenta que, al eliminarla, los datos de la alarma ya no se podrán visualizar ni ser recuperados. |

| RF004 | Editar |
| --- | --- |
| Descripción | El sistema permitirá modificar cualquiera de los datos ya ingresados en alarmas ya previamente creadas por el mismo usuario. Teniendo en cuenta que, al modificarlos, se reiniciará el tiempo de duración que tenía el medicamente o hábito.
 |

| RF005 | Agregar |
| --- | --- |
| Descripción | El sistema permitirá agregar una alarma. Los datos que se deben de ingresar para agregar una alarma son:
- Nombre del medicamento
- Hora de primera toma
- Periodo de tiempo (cada cuanto tiempo se debe de tomar el medicamento)
- Fecha en la cual se auto desactivará la alarma
- Notas (recordatorios de especificaciones)
 |

| RF006 | Buscador de alarma |
| --- | --- |
| Descripción | En la parte superior se encontrará una barra de búsqueda el cual desplegará las opciones alarmas existentes relacionadas con la búsqueda, las opciones serán seleccionables y redirigirán al usuario a editar la alarma |

| RF007 | Mostrar alarmas |
| --- | --- |
| Descripción | El sistema mostrará una lista de todas las alarmas creadas por el usuario, tanto las que se encuentran activadas como las que se encuentran desactivadas. |

**3.2.2 No funcionales**

| RNF001 | Conteo automático |
| --- | --- |
| Descripción | El sistema empezará su conteo de duración de acuerdo al tiempo ingresado por el usuario una vez guardado el recordatorio, con los usos que sean posibles durante ese tiempo.
 |

| RNF002 | Suspensión automática |
| --- | --- |
| Descripción | El sistema desactivará automáticamente los recordatorias una vez que se llegue a la fecha indicada como fecha de auto desactivación.
 |

| RNF003 | Notificaciones |
| --- | --- |
| Descripción | El sistema mandará la notificación de uso del medicamente cada que cumple el periodo de ingresado (hr) con los siguientes datos:
- Nombre del medicamento
- Notas agregadas por el usuario
 |

| RNF004 | Asistente Inteligente |
| --- | --- |
| Descripción | Se podrá acceder a la aplicación mediante el asistente inteligente d Google, el usuario podrá únicamente agregar o eliminar alarmas haciendo uso del asistente |

| RNF005 | Sistemas compatibles |
| --- | --- |
| Descripción | La aplicación se desarrollaá con android 4.0.3(Ice Cream Sandwich) API 15 para tener una compatibilidad del 100% con cualquier dispositivo. |

**3.2.3 Mapeo de requerimientos**

| **Requerimiento** | **Objetivo** |
| --- | --- |
| Funcionales |
 |
| RF001 | A |
| RF002 | A |
| RF003 | C |
| RF004 | D |
| RF005 | D |
| RF006 | A |
| RF007 | B |
| No Funcionales |
 |
| RNF001 | E |
| RNF002 | E |
| RNF003 | C |
| RNF004 | G |
| RNF005 | F |

**4. Asistente Inteligente.**

**4.1 Definición** 
* ¿Qué es dialogflow? 

El asistente de este proyecto se basa en Dialogflow, que es una plataforma con comprensión del lenguaje natural que facilita el diseño de una interfaz de usuario de conversación y su integración a una aplicación para dispositivos móviles, aplicaciones web, dispositivos, bots, sistemas de respuesta de voz interactiva, entre otros. Dialogflow da lugar a nuevas y atractivas formas para que los usuarios interactúen con tu producto. Resumiendo, los pasos que sigue esta herramienta de google, tenemos que primero el usuario ingresa información, se hace una consulta de esta información, se determina el intent y se manda a este, el intent entra en proceso y hace sus acciones y respuestas necesaria, se envían parámetros al proyecto para finalizar con datos que determinan alguna acción y mandarlos como respuesta.

**4.2 Creación del asistente**

**4.2.1 Agente**
Para empezar, entendamos que todos los procesos y recursos de cualquier proyecto se manejan directamente desde la plataforma de Dialogflow, estos se sincronizan al proyecto en cuestión por medio de una llave que se otorga al iniciar un nuevo agente. Para hacer esto primero se necesita crear un dicho “agente”, que es un agente virtual que maneja las conversaciones con los usuarios finales. Es un módulo de comprensión del lenguaje natural que comprende los matices del lenguaje humano. Un agente de Dialogflow es similar a un agente de un centro de llamadas humano. Debes entrenarlos para que se encarguen de las situaciones de conversación esperadas, y el entrenamiento no tiene que ser demasiado explícito. Manejará todos los procesos: intents, acciones, entidades, parámetros, etcétera. Para crear el agente:

1) Ir a la consola de Dialogflow. 

2) Si se solicita, acceder a la consola de Dialogflow. 

3) Hacer clic en Create agent, en el menú de la barra lateral izquierda. . 

4) Ingresa el nombre del agente, y el idioma y la zona horaria predeterminados. (Importante) 

El agente de este proyecto “AlArmando” está dedicado a reconocer y extraer horarios y periodos de tiempo relacionados a medicamentos de nuestros usuarios, así como datos adicionales que se le pueden proporcionar.Está configurado en GMT-5.

**4.2.2 Intents**
Un intent clasifica la intención del usuario final para un turno de conversación. Para cada agente defines muchos intents; tus intents combinados pueden manejar una conversación completa. Cuando un usuario final escribe o dice algo, lo que se denomina expresión de usuario final, Dialogflow hace coincidir la expresión del usuario final con el mejor intent en tu agente. La coincidencia de un intent también se conoce como clasificación de intent. 

Un intent contiene los siguientes elementos básicos: frases de entrenamiento, acción, parámetros, respuestas, contextos y eventos. Para crearlo nos dirigimos a la parte izquiera de la pantalla en el apartado "intents" y seleccionamos CREATE INTENT. Tomemos como ejemplo el intent de nuestro agente “establecerAlarmaConFecha”: primero le dimos un nombre que vaya de acuerdo a la función que realizará, después le dimos las frases de entrenamiento pensando en lo que el usuario final podría decir mientras se buscaba no ser repetitivos. Se recomienda dar al menos 10 frases de entrenamiento. Por nuestra parte, le aportamos frases como “programa una alarma a las 8:50 p.m hasta el día 20 de agosto del 2020”, “quiero una alarma a las 7 de la mañana con fecha final en el día 15 de octubre”; como se puede observar, se introdujeron sinónimos de los verbos y conectores para la duración, buscando variar y que el entrenamiento sea más completo.

**4.2.3 Acción**
El campo de acción, que se encuentra en el apartado de "intents", es un campo de conveniencia sencillo que ayuda a ejecutar la lógica en el servicio. 

Cuando un intent coincide en el tiempo de ejecución, Dialogflow proporciona el valor de acción a la solicitud de webhook de entregas o la respuesta de interacción de la API. Se puede utilizar para activar lógica específica en tu servicio. En nuestro caso simplemente nos sirvió para identificar de qué intent se trataba al momento de dar el query, llenamos el campo Acción de este intent con “AlarmaConHorario”. 

**4.2.4 Parámetros**
Cuando un intent coincide en el entorno de ejecución, Dialogflow proporciona los valores extraídos de la expresión del usuario final como parámetros. Cada parámetro tiene un tipo, llamado tipo de entidad, que dicta cómo se extraen los datos. Nuestro equipo configuró las frases de entrenamiento del intent en cuestión para que solamente sacara 2 parámetros: tiempo y fecha. La configuración de las frases para los parámetros es tan fácil como darle una selección a ciertas partes que se repiten y un mismo tipo de entidad. Estos parámetros tienen demasiada relevancia, pues es con lo que trabajaremos en nuestro programa.

**4.2.5 Entidades**
Cada parámetro de intent tiene un tipo, denominado tipo de entidad, que determina de forma exacta cómo se extraen los datos de una expresión de usuario final.

Dialogflow proporciona entidades del sistema predefinidas que pueden coincidir con muchos tipos comunes de datos. Por ejemplo, hay entidades del sistema que coinciden con fechas, horas, colores, direcciones de correo electrónico, etcétera. También puedes crear tus propias entidades personalizadas para detectar coincidencias en datos personalizados. Por ejemplo, podrías definir una entidad vegetal que coincida con los tipos de vegetales disponibles para la compra con un agente de supermercado. De igual manera, las entidades creadas pueden ser compuestas al tratarse de dos o más entidades simples.

Como se mencionó en el apartado anterior, el intent "establecerAlarmaConFecha" de nuestro asistente se centra en la extracción de los datos $time y $date (predefinidos de Dialogflow). El primer valor $time tiene una cadena como salida en formato ISO-8601; el segundo valor, $date, tiene como salida un string en el mismo formato ISO-8601. Si introducimos la frase “alarma a las 8:30 p.m hasta el día 21 de octubre del 2020” los parámetros que la inteligencia obtendrá son, por parte de el tiempo, {“20:30:00”}, por parte de la fecha, {“2020-10-21"}.

**4.2.6 Respuestas**
Los intents tienen un controlador de respuestas integrado que puede mostrar respuestas después de la coincidencia del intent. Esta característica solo admite respuestas estáticas, aunque puedes usar referencias del parámetro en estas respuestas para volverlas dinámicas en algún sentido. Esto es útil para resumir la información proporcionada por el usuario final. Por ejemplo, la respuesta de tu intent podría ser: “De acuerdo, reservé una habitación para ti el $date”. Para asignarlas nos dirigimos a su respectivo apartado y escribimos la resuesta que queremos que dé la inteligencia al usuario cuando reconozca el intent. El nuestro cuenta con dos respuestas predeterminadas: “Alarma establecida a las $time.original con fecha final en el día $date.original” y “Programando alarma a las $time.original con fecha final en el día $date.original”. 

**4.2.7 Contextos**
Los contextos de Dialogflow son similares al contexto del lenguaje natural. Si una persona te dice “son naranjas”, necesitas contexto para entender a qué se refieren. Del mismo modo, para que Dialogflow maneje una expresión de usuario final como esa, debe proporcionarse un contexto con el fin de que coincida de forma correcta con un intent. Mediante los contextos, puedes controlar el flujo de una conversación. Si quieres configurar contextos para un intent, debes establecer contextos de entrada y salida, que se identifican mediante nombres de strings.  

Nuestra aplicación no necesitó de contextos, no es tan complicada. 

**4.2.8 Eventos**
En condiciones normales, se detecta una coincidencia con un intent cuando una expresión de usuario final coincide con una frase de entrenamiento del intent. Sin embargo, también puedes activar intents mediante eventos. Los eventos se pueden invocar de muchas maneras. 

Hay dos tipos de eventos: 

* Eventos de plataforma: Estos eventos integrados los proporcionan las integraciones de la plataforma. Se invocan cuando se producen eventos específicos de la plataforma. Por ejemplo, la integración en Facebook invoca el evento FACEBOOK_LOCATION cuando un usuario final acepta o rechaza una solicitud para acceder a su ubicación. 

* Eventos personalizados: Son eventos que tú defines. Puedes invocarlos mediante una entrega o la API. Por ejemplo, puedes establecer una alerta temporizada durante una conversación, lo que invoca un evento en un momento determinado. Este evento podría activar un intent que alerta al usuario final acerca de algo. 

En el presente proyecto decidimos  detectar intents con expresiones de usuario solamente, no optamos por eventos.

**4.3 Implementación del asistente**
Para implementar un asistente se necesita activar la integración que se quiere directamente en la plataforma, existen muchas opciones de las cuales destacan las basadas en texto como son facebook messenger, twitter, slack, hangouts chat, line, entre otros. También existen algunas opciones para telefonía. 

Para implementar nuestro asistente a la app nosotros no necesitamos activar ninguna integration, ya que se trata de una app en Android Studio.  

En la aplicación se hookea los servicios de la plataforma con la variable AIService y con la función AIConfiguration poniendo nuestra clave del agente en esta última, también es necesario darle un idioma y un motor de reconocimiento. A partir de aquí se implementan las clases y se utilizan de la manera que se quiera. En nuestro caso el idioma elegido fue el español y el sistema predeterminado, también, al ser un listener, estuvimos en la necesidad de implementar las clases necesarias para así manejar los parámetros de una manera más eficiente centrándonos en la clase onResult. 
  
**5. Casos de uso.**

**CU01** : Agregar alarma de medicina.
**Descripción** : El usuario desea agregar la alarma de una medicina.
**Secuencia** :

1. El usuario abre la aplicación.
2. El usuario presiona el botón de añadir alarma.
3. El usuario coloca todos los datos requeridos.
4. La alarma comienza a funcionar al momento que el usuario presiona guardar.
5. El usuario puede agregar otra alarma o regresar a la página principal presionando la flecha para regresar.

**Salidas alternativas:**

Si el usuario ya no desea crear alguna alarma, presiona la flecha para regresar a la página principal.

**CU02** : Editar alarma de medicina.
**Descripción** : El usuario desea editar la alarma de una medicina.
**Secuencia** :

1. El usuario abre la aplicación.
2. El usuario selecciona la opción buscar.
3. El usuario coloca en la barra de búsqueda el nombre de la alarma que desea editar.
4. El usuario selecciona el nombre de la alarma.
5. El usuario modifica el o los datos que desea cambiar.
6. El usuario presiona el botón de guardar.
7. El usuario presiona la flecha para regresar a la página principal.

**Salidas alternativas:**

Si el usuario ya no desea editar alguna alarma, presiona la flecha para regresar a la página principal.

**CU03** : Eliminar alarma de medicina.
**Descripción** : El usuario desea eliminar la alarma de una medicina.
**Secuencia** :

1. El usuario abre la aplicación.
2. El usuario selecciona la opción buscar.
3. El usuario coloca en la barra de búsqueda el nombre de la alarma que desea eliminar.
4. El usuario selecciona el nombre de la alarma.
5. El usuario presiona el botón de eliminar.
6. El usuario presiona la flecha para regresar a la página principal.

**Salidas alternativas:**

Si el usuario ya no desea eliminar alguna alarma, presiona la flecha para regresar a la página principal.

**CU04** : Agregar alarma de medicina usando el asistente inteligente.
**Descripción** : El usuario desea agregar la alarma de una medicina usando únicamente el asistente inteligente.
**Secuencia** :

1. El usuario abre la aplicación.
2. El usuario selecciona la opción asistente.
3. El usuario, como ejemplo, le puede decir al asistente: &quot;Agregar alarma&quot;.
4. Se hará una serie de preguntas y respuestas entre el usuario y el asistente para adquirir todos los datos necesarios.
5. Al finalizar, el asistente dirá: &quot;Alarma agregada&quot;, se cerrará automáticamente y la alarma estará activa a partir de ese momento.
6. El usuario se mantendrá en la página principal durante todo el proceso.

**Salidas alternativas:**

Si el usuario ya no desea agregar alguna alarma, puede decir: &quot;cancelar&quot;, cancelando automáticamente el proceso.

**5.1 Diagrama de casos:**

![casoUso](https://raw.githubusercontent.com/pajaroloco86/ProyectoPE/master/casos.png)

**6. Proceso.**

**6.1 Estándares de codificación**

Se empleará el uso del estándar básico de programación:

Los nombres de las variables serán sustantivos que hagan referencia al contexto de la variable, la primera letra de la primera palabra será minúscula y el inicio de cada palabra, mayúscula, no se empleará el uso de guion o guion bajo entre palabras, las palabras estarán unidas una tras otra.

Uso correcto de la indentación, se empleará el uso de llaves, en donde después de abrir una llave se creará un salto de línea y las líneas de código se desplazarán un TAB por cada llave abierta, es decir, si se abren dos llaves, la línea de código debe moverse dos TAB, el cierre de llave va después de la última línea de código.

Funciones, las funciones usarán el mismo tipo de nombrado que las variables excepto que en vez de sustantivos serán verbos que dan contexto a la acción de la función.

Archivos, los archivos creados que sean requeridos durante el proceso de desarrollo, se nombraran de forma que hagan referencia al contexto y/o contenido del mismo

Librerías, las librerías a emplear serán las proporcionadas por el propio lenguaje y las creadas por el mismo equipo de desarrollo

Comentarios, para las funciones se emplearán principalmente dos tipos de comentarios, el generado por la extensión Javadoc Tools de la aplicación Visual Studio Code, y comentarios sencillos, preferentemente agregados dentro del comentario de Javadoc, en donde se explica brevemente la función

Como documentación se utilizará JavaDoc proveniente del Java Developer Kit

**6.2 Proceso de desarrollo.**

Se empleará el método scrum, pero enfocándonos en que el sistema sea iterativo-incremental en donde se llevará la necesidad de tener un análisis, un diseño, la codificación y las pruebas (en ese mismo orden), para cada que se le agregue una funcionalidad conforme pase el tiempo para permitir la evolución del producto.

Herramientas:

Como principal entorno de desarrollo se empleará Android Studio, como principal punto de trabajo entre los miembros del equipo se utilizará GitHub, se usará Visual Studio Code como apoyo para la generación de la documentación interna del codigo.

**7. Trabajo en equipo**

**7.1 Roles:**

Alexis Ake: _Scrum team_

Diego de Gante: _Scrum team_

Mario Chan: _Scrum team_ _ **/ Scrum master** _

Pedro Cauich: _Scrum team_

**7.2 Esquemas de monitoreo:**

El monitoreo del equipo estará contenido en el calendario, donde se irán apuntando los avances logrados cada determinado tiempo y serán comparados con los avances planeados.

**7.3 Bitácoras:**

Conforme avanzamos en el proceso de desarrollo, iremos anotando cada una de los avances y cambios que se vayan haciendo al sistema, para tener un control sobre el proceso.

[https://1drv.ms/w/s!AnplW1evppzUh2FakbGST47N7krN?e=BUNP7L](https://1drv.ms/w/s!AnplW1evppzUh2FakbGST47N7krN?e=BUNP7L)

**7.4 Métrica para evaluar avance individual:**

Al terminar con cada sprint, en la reunión de revisión, se realizarán pruebas unitarias a cada módulo trabajado, se analizará entonces la eficiencia del código, así como su extensión. También añadiremos la revisión del tiempo dedicado de cada integrante del equipo como un recurso adicional para la medición.

El porcentaje será dado se acuerdo a las pruebas unitarias en el &quot;retrospective sprint&quot;, por lo que cada junta deberán tener un porcentaje de 100% para que al final se saque un promedio entre los 6 sprint para obtener su % de contribución.

_El desglose de la métrica individual esta agregada junto con la bitácora._

retrospective sprint\_ finalpercentage:

Alexis Ake: 100%

Diego de Gante: 100%

Mario Chan: 100%

Pedro Cauich: 100%

**7.5 Avance grupal:**

Haremos reuniones donde se analizará el avance del proyecto en relación a lo establecido, para poder dar paso a la siguiente etapa de desarrollo, tomando en cuenta la asistencia y su avance en la construcción del proyecto para saber que cada uno cumpla con su parte establecida.

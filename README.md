# HospitalManagement: Clinical Data Reporting System

1. Spring Boot REST serice that facilitates consumer to keep track of Patient's detail and their clinical data.         
2. Provides two different end-points. One for keeping record of medicalData ("/healthRecords/saveRecord") and another to keep track of patient's details        
("/patient/*").     
3. Provides the service to calculate "Body Mass Index (BMI)" for the patient and save it in database and retrieve them as needed.                 


## Tech Stack

1. Build Spring Boot starter project in STS by adding the following dependency to the project.           
cxf-spring-boot-starter-jaxrs, com.fasterxml.jackson.jaxrs, mysql-connector-java, spring-boot-starter-data-jpa, org.springframework.boot.       

2. Configure the cxf-spring realted properties in application.properties:
server.servlet.context-path=/medicalApi                      
cxf.path=/                 
cxf.jaxrs.component-scan=true                
cxf.jaxrs.classes-scan-packages=org.clinical.records,com.fasterxml.jackson.jaxrs                 
spring.datasource.url=jdbc:mysql://localhost:3306/hospital?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC     
spring.datasource.username=****                
spring.datasource.password=******                    

3. Create databases "patient" and "medicaldata" using following SQL commands and insert record as well:         
I. CREATE TABLE patient (id int not null auto_increment, last_name varchar(255) not null, first_name varchar(255) not null, age int, primary key(id));
                    
II. CREATE TABLE medicaldata(id int NOT NULL auto_increment, patient_id int NOT NULL, component_name varchar(255) NOT NULL, component_value varchar(255) NOT NULL, measured_timestamp timestamp default current_timestamp, primary key(id), constraint foreign key(patient_id) references patient(id));           

III. INSERT INTO patient(last_name, first_name, age) VALUES("Mouse", "Mickey", 23), ("Miccain", "John", 20), ("Bravo", "Scubby", 30), ("Fernandos", "Joseph", 24);                   
        
IV. INSERT INTO medicaldata(patient_id, component_name, component_value) VALUES(2, "BP", "82/121"), (2,"HeartBeat", "83"), (3, "BP", "87/121"), (3,"HeartBeat", "85"), (4, "BP", "82/124"), (4,"HeartBeat", "90");                   

4. Create Entity classes, "Patient" and "MedicalRecord" that corresponds to the database table "patient" and "medicaldata" respectively. Mark.  
each classes with JPA annotations. Such as @Entity, @Id, @GeneratedValue(strategy=GenerationType.IDENTITY),         
@JsonIgnoreProperty({"patient"})           

@ManyToOne(fetch=FetchType.LAZY) 
@JoinColumn(name="patient_id", nullable=false) ==> Entails that the instance variable is a foreign-key in the table.      

@OneToMany(fetch=FetchType.Eager, cascade=Cascade.ALL, mappedBy="patient")           

5. Create Repository classes, PatientRepo and MedicalRecordRepo for each table and extend JPARespository<Patient, Integer> & 
JPARepository<MedicalRecord, Integer> respectively.

6. Create the "/patient" and "/healthRecords" endpoints and mark them with JAX-RS annotations. Use Repo classes from earlier step to perform CRUD operations 
in the database.       


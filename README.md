An application to check the availability of beds in hospital and reserve the bed for the patient.

The use cases of this application are 

    1) The viewer/patient can check the availability of the beds like normal beds, oxygen beds, ICU beds , Ventilators separately district wise. 
    2) The viewer/patient can reserve a bed if necessary by registering and providing the supporting details.
    3) The viewer/patient can cancel the reserved bed. 
    4) The Hospitals can register and provide the available bed details. 
    5) The respective hospitals can update the availability of beds. 
    6) The hospitals can admit the patient and discharge the patient.
    
FEATURES:

Hospital MODE: 

    1) Register the hospital details.
        i)Only the hospitals in government registry can register. 
    2) Update the availability of beds in category wise. 
    3) Acknowledge the admission of the patient. 
    4) Discharge the patient. 
    5) View the details of the patients 
    6) View the available bed details. 
    7) Update hospital details. 
    8) Deregister Hospital.
    
USER MODE: 

    1) The user can check the availability of the beds like normal beds, oxygen beds, ICU beds, Ventilators separately district wise without registering. 
    2) The user can reserve a bed in a specific hospital by registering and providing the supporting details(It will be validated).
        i) Only covid positive patients can reserve bed..it will be cross verified with government DB. 
        ii) The Reservation is valid for only 24 hours after that the reservation will automatically cancelled.
    3) The user can cancel the reserved bed. 
    4) The user can view the reservation details.
    5)Update user details.
    6)Deregister user.

ALL the required validations are done.

Software Requirements:-

    1)Mysql server and work bench
    2)JDK 14.0.1 
    3)IDE - Recommended VS code 

Steps:- 
1)Download and install MYSQL.
2)Import SQl files.
3)Run the Program.

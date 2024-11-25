![1](https://github.com/user-attachments/assets/3ccfa77c-3afb-4852-88e0-f36b180ee2ab)

# Zeep Services

**Zeep Services**: Streamlined Travel Management for Students and Drivers

Zeep Service is a cutting-edge travel management application designed to simplify the daily commute for students traveling between home and classes. This app connects students and drivers in one platform, enabling efficient ride management and seamless communication.

Checkout Video Demo: [Video Demo](https://youtu.be/m9zD_PR5E1Q)


## Features

- Student-Driver Coordination: Students can select rides through flexible subscription plans (daily or long-term) and cancel trips before departure. Drivers get real-time visibility of their passengers, ensuring a smooth and informed journey.
- Notifications: Both drivers and students receive timely updates about upcoming trips, with options to accept or decline rides.
- Complaint Management: A dedicated WhatsApp bot allows users to log complaints, monitored by the support team.
- Data Insights: Dashboards provide a comprehensive view of user activity, enhancing operational clarity.
- Crash Monitoring: Integrated Crashlytics ensures swift error detection and reporting, pinpointing issues to enhance app reliability.
- Automation: Supported by Quickwork, a leading automation solution, for smooth backend operations.


## Screenshots

![2](https://github.com/user-attachments/assets/031624f1-f017-49e5-8dcc-33c88984b37b)

![3](https://github.com/user-attachments/assets/86222ebf-d598-49ed-aba0-1b9bbc1f19dd)

![4](https://github.com/user-attachments/assets/606a311a-44db-4516-bad0-cd34129de947)

![5](https://github.com/user-attachments/assets/376bc12a-8afa-4fa7-9443-930ccff39962)

## Supporting Services

### QuickWork Dashboards
Zeep Service leverages **QuickWork Automation** to seamlessly integrate various services and enhance user experience. The automation enables smooth connectivity between the application and platforms such as:

- WhatsApp: For complaint registration and communication with the support team.
- Google Calendar: For scheduling and managing ride notifications and bookings.
- Google Sheets: For maintaining and analyzing data records for operational efficiency.

These integrations provide robust backend support, ensuring the app operates efficiently and delivers a seamless experience for both students and drivers. QuickWork's automation simplifies processes, reduces manual effort, and ensures reliability at scale.

![screencapture-automation-quickwork-co-connections-634f1442f6e61740c82be339-2024-07-28-04_05_25](https://github.com/user-attachments/assets/3ba1ddac-bcde-49ea-9e25-507d85df4300)

![screencapture-automation-quickwork-co-2024-07-28-04_05_10](https://github.com/user-attachments/assets/fdc32af1-97b3-4f70-9db6-0058580db517)

![screencapture-automation-quickwork-co-2024-07-28-04_04_30](https://github.com/user-attachments/assets/6db2acc5-c80e-4121-894a-d7e1621d64b0)

![screencapture-automation-quickwork-co-2024-07-28-03_59_39](https://github.com/user-attachments/assets/dd9d9efb-1d8f-424f-b92a-7d7e0a78b7f2)

### Firebase Services
The Zeep Service app leverages Firebase to deliver a seamless, reliable, and optimized user experience through the following features:

- Analytics: Real-time crash reports and insights help monitor and resolve app issues efficiently, ensuring enhanced reliability.
- Authentication: Securely authenticates users directly within the application for effortless and safe access.
- Firestore Database: Stores and manages relevant data for users, drivers, and customers, providing quick and scalable data retrieval.
- Cloud Functions
  - **HTTP Triggers**: Automatically trigger actions when a document is created on the student side, ensuring up-to-date operations.
  - **Pub/Sub Events**: Update the daily count of incoming students to keep all stakeholders informed.
- Extensions
  - **Image Resizing**: Reduces image dimensions to optimize space and improve app performance on the client side.
  - **URL Shortener**: Generates compact URLs to enhance usability and reduce data payload.
With Firebase, the application achieves lightweight client-side operations while handling complex server-side tasks effectively, ensuring a superior and scalable user experience.

![6](https://github.com/user-attachments/assets/8a9bdf13-67d2-4b8f-96b0-03bd387629ab)

![7](https://github.com/user-attachments/assets/4ba85f0b-9e36-4c27-a9aa-1d55ff7577da)

![8](https://github.com/user-attachments/assets/bef2476f-ff93-4aae-8801-f74251bb107f)

![9](https://github.com/user-attachments/assets/5e12f469-b709-47ec-8e4f-a2b48c623bdb)

![10](https://github.com/user-attachments/assets/d45ac8aa-7791-453a-8cec-beca794b5b5a)

![11](https://github.com/user-attachments/assets/49c96d91-dcaa-4379-801e-e1b9df176406)

![12](https://github.com/user-attachments/assets/447df215-d2b1-44b0-bb23-4033e96aec38)

![13](https://github.com/user-attachments/assets/5bdb20aa-e3b3-4232-abe6-c7c5c084d603)

![14](https://github.com/user-attachments/assets/85ee7aac-9fce-485f-a4f2-edaf0441100b)

![15](https://github.com/user-attachments/assets/055e4c81-f6c9-4e58-83d4-38bb23eaeaa3)

### Google Assistant Integration  

Zeep Service takes accessibility to the next level by integrating **Google Assistant**, allowing users to open the app effortlessly using voice commands. By implementing the **OPEN_APP_FEATURE** and publishing an internal release on the Google Play Store, users can simply say:  

> **"Hey Google, open Zeep Service"**  

This feature enhances user convenience, particularly for on-the-go users, ensuring they can access the app hands-free. This integration demonstrates a commitment to innovation and improving the user experience by embracing cutting-edge voice command technology.  

## Driver and Student Dashboards Overview: Detailed Insights and Management Features

### Student's Dashboard 
![6](https://github.com/user-attachments/assets/e769cb57-55e7-47e7-8b8c-e2bf3450d412)

### Driver's Dashboard 
![5](https://github.com/user-attachments/assets/9e18c8fb-2a2b-4d56-8b49-a113f32f9d71)

## Custom Rating View Implementation: Developed from Scratch and Released on Jetpack.io
In this project, I developed a custom Rating View component entirely from scratch, which allows users to rate services or experiences with ease. The view features star ratings, with a clean and interactive interface for better user engagement. The custom Rating View provides full flexibility, enabling developers to adjust its appearance, functionality, and styling based on project needs.

To ensure that this component can be easily integrated into other projects, I published it on Jetpack.io, making it available for anyone to use or customize for their own Android applications.


![Link](https://github.com/user-attachments/assets/caacfcf8-4b25-4b47-846d-dd0dd2999178)

### Key Features
- Fully customizable star ratings for both static and dynamic use cases.
- Smooth interaction with clear visual feedback to users.
- Easily integrated into any Android project via Jetpack.io.
- Support for different themes, sizes, and customizable attributes such as colors, radius, and spacing.

Library Link: [Star Android Library](https://github.com/dhairyapandya05/Star_Android_Library)

![1](https://github.com/user-attachments/assets/3ccfa77c-3afb-4852-88e0-f36b180ee2ab)

# Zeep Services

**Zeep Services**: Streamlined Travel Management for Students and Drivers

Zeep Service is a cutting-edge travel management application designed to simplify the daily commute for students traveling between home and classes. This app connects students and drivers in one platform, enabling efficient ride management and seamless communication.

Checkout Video Demo: [Video Demo](https://youtu.be/m9zD_PR5E1Q)


## Features

- Student-Driver Coordination: Students can select rides through flexible subscription plans (daily or long-term) and cancel trips before departure. Drivers get real-time visibility of their passengers, ensuring a smooth and informed journey.
- Notifications: Both drivers and students receive timely updates about upcoming trips, with options to accept or decline rides.
- Complaint Management: A dedicated WhatsApp bot allows users to log complaints, monitored by the support team.
- Data Insights: Dashboards provide a comprehensive view of user activity, enhancing operational clarity.
- Crash Monitoring: Integrated Crashlytics ensures swift error detection and reporting, pinpointing issues to enhance app reliability.
- Automation: Supported by Quickwork, a leading automation solution, for smooth backend operations.


## Screenshots

![2](https://github.com/user-attachments/assets/031624f1-f017-49e5-8dcc-33c88984b37b)

![3](https://github.com/user-attachments/assets/86222ebf-d598-49ed-aba0-1b9bbc1f19dd)

![4](https://github.com/user-attachments/assets/606a311a-44db-4516-bad0-cd34129de947)

![5](https://github.com/user-attachments/assets/376bc12a-8afa-4fa7-9443-930ccff39962)

## Supporting Services

### QuickWork Dashboards
Zeep Service leverages **QuickWork Automation** to seamlessly integrate various services and enhance user experience. The automation enables smooth connectivity between the application and platforms such as:

- WhatsApp: For complaint registration and communication with the support team.
- Google Calendar: For scheduling and managing ride notifications and bookings.
- Google Sheets: For maintaining and analyzing data records for operational efficiency.

These integrations provide robust backend support, ensuring the app operates efficiently and delivers a seamless experience for both students and drivers. QuickWork's automation simplifies processes, reduces manual effort, and ensures reliability at scale.

![screencapture-automation-quickwork-co-connections-634f1442f6e61740c82be339-2024-07-28-04_05_25](https://github.com/user-attachments/assets/3ba1ddac-bcde-49ea-9e25-507d85df4300)

![screencapture-automation-quickwork-co-2024-07-28-04_05_10](https://github.com/user-attachments/assets/fdc32af1-97b3-4f70-9db6-0058580db517)

![screencapture-automation-quickwork-co-2024-07-28-04_04_30](https://github.com/user-attachments/assets/6db2acc5-c80e-4121-894a-d7e1621d64b0)

![screencapture-automation-quickwork-co-2024-07-28-03_59_39](https://github.com/user-attachments/assets/dd9d9efb-1d8f-424f-b92a-7d7e0a78b7f2)

### Firebase Services
The Zeep Service app leverages Firebase to deliver a seamless, reliable, and optimized user experience through the following features:

- Analytics: Real-time crash reports and insights help monitor and resolve app issues efficiently, ensuring enhanced reliability.
- Authentication: Securely authenticates users directly within the application for effortless and safe access.
- Firestore Database: Stores and manages relevant data for users, drivers, and customers, providing quick and scalable data retrieval.
- Cloud Functions
  - **HTTP Triggers**: Automatically trigger actions when a document is created on the student side, ensuring up-to-date operations.
  - **Pub/Sub Events**: Update the daily count of incoming students to keep all stakeholders informed.
- Extensions
  - **Image Resizing**: Reduces image dimensions to optimize space and improve app performance on the client side.
  - **URL Shortener**: Generates compact URLs to enhance usability and reduce data payload.
With Firebase, the application achieves lightweight client-side operations while handling complex server-side tasks effectively, ensuring a superior and scalable user experience.

![6](https://github.com/user-attachments/assets/8a9bdf13-67d2-4b8f-96b0-03bd387629ab)

![7](https://github.com/user-attachments/assets/4ba85f0b-9e36-4c27-a9aa-1d55ff7577da)

![8](https://github.com/user-attachments/assets/bef2476f-ff93-4aae-8801-f74251bb107f)

![9](https://github.com/user-attachments/assets/5e12f469-b709-47ec-8e4f-a2b48c623bdb)

![10](https://github.com/user-attachments/assets/d45ac8aa-7791-453a-8cec-beca794b5b5a)

![11](https://github.com/user-attachments/assets/49c96d91-dcaa-4379-801e-e1b9df176406)

![12](https://github.com/user-attachments/assets/447df215-d2b1-44b0-bb23-4033e96aec38)

![13](https://github.com/user-attachments/assets/5bdb20aa-e3b3-4232-abe6-c7c5c084d603)

![14](https://github.com/user-attachments/assets/85ee7aac-9fce-485f-a4f2-edaf0441100b)

![15](https://github.com/user-attachments/assets/055e4c81-f6c9-4e58-83d4-38bb23eaeaa3)

### Google Assistant Integration  

Zeep Service takes accessibility to the next level by integrating **Google Assistant**, allowing users to open the app effortlessly using voice commands. By implementing the **OPEN_APP_FEATURE** and publishing an internal release on the Google Play Store, users can simply say:  

> **"Hey Google, open Zeep Service"**  

This feature enhances user convenience, particularly for on-the-go users, ensuring they can access the app hands-free. This integration demonstrates a commitment to innovation and improving the user experience by embracing cutting-edge voice command technology.  

## Driver and Student Dashboards Overview: Detailed Insights and Management Features

### Student's Dashboard 
![6](https://github.com/user-attachments/assets/e769cb57-55e7-47e7-8b8c-e2bf3450d412)

### Driver's Dashboard 
![5](https://github.com/user-attachments/assets/9e18c8fb-2a2b-4d56-8b49-a113f32f9d71)

## Custom Rating View Implementation: Developed from Scratch and Released on Jetpack.io
In this project, I developed a custom Rating View component entirely from scratch, which allows users to rate services or experiences with ease. The view features star ratings, with a clean and interactive interface for better user engagement. The custom Rating View provides full flexibility, enabling developers to adjust its appearance, functionality, and styling based on project needs.

To ensure that this component can be easily integrated into other projects, I published it on Jetpack.io, making it available for anyone to use or customize for their own Android applications.


![Link](https://github.com/user-attachments/assets/caacfcf8-4b25-4b47-846d-dd0dd2999178)

### Key Features
- Fully customizable star ratings for both static and dynamic use cases.
- Smooth interaction with clear visual feedback to users.
- Easily integrated into any Android project via Jetpack.io.
- Support for different themes, sizes, and customizable attributes such as colors, radius, and spacing.

Library Link: [Star Android Library](https://github.com/dhairyapandya05/Star_Android_Library)

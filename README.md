# 🌟 SpringJarvis – AI Powered Voice Assistant  

![Java](https://img.shields.io/badge/Java-17-orange)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=springboot)  
![Spring AI](https://img.shields.io/badge/Spring%20AI-Intelligent%20Apps-6DB33F?logo=spring&logoColor=white)  
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-darkgreen?logo=mongodb)  
![Speech](https://img.shields.io/badge/Text--to--Speech-ElevenLabs-yellow)  

---

## 📌 Description  

**SpringJarvis** is an **AI-powered backend assistant** built with **Spring Boot, Spring AI, Ollama, MongoDB, and ElevenLabs API**.  
It enables **real-time Voice-to-Voice interactions** by combining **speech-to-text, AI text response generation, and text-to-speech conversion**.  

### 🔑 Features  
- 🎙 **Voice-to-Voice Conversion**: Talk to the assistant and get real-time AI-generated voice responses.  
- 🧠 **Spring AI + Ollama**: Handles natural text-to-text conversations.  
- 🗣 **Text-to-Speech (TTS)**: Powered by ElevenLabs for realistic MP3 voice output.  
- 🎧 **Speech-to-Text (STT)**: Converts uploaded voice into text.  
- ⏰ **Alarm Management (CRUD)**: Alarms stored in **MongoDB** trigger AI responses and play as MP3.  
- 📩 **Email Notifications**: Integrated with Java Mail Sender to send email alerts.  
- 🛠 **Backend-Only Implementation**: Focused on server-side intelligence and APIs.  

---

## 🖼 Screenshots  

| Voice-to-Voice Conversation | Alarm Trigger |  
|-----------------------------|---------------|  
| ![Voice-to-Voice](https://github.com/nagenDev/SpringJarvis-AI-Powered-Voice-Assistant/blob/main/Images%202/Screenshot%20(596).png) | ![Alarm Trigger](https://github.com/nagenDev/SpringJarvis-AI-Powered-Voice-Assistant/blob/main/Images%202/Screenshot%20(605).png) |  

| Alarm CRUD (Insert in MongoDB) | Get Alarm by ID |  
|--------------------------------|----------------|  
| ![Alarm Insert](https://github.com/nagenDev/SpringJarvis-AI-Powered-Voice-Assistant/blob/main/Images%202/Screenshot%20(607).png) | ![Get by ID](https://github.com/nagenDev/SpringJarvis-AI-Powered-Voice-Assistant/blob/main/Images%202/Screenshot%20(608).png) |  

| Update Alarm by ID | Delete Alarm by ID |  
|--------------------|-------------------|  
| ![Update Alarm](https://github.com/nagenDev/SpringJarvis-AI-Powered-Voice-Assistant/blob/main/Images%202/Screenshot%20(610).png) | ![Delete Alarm](https://github.com/nagenDev/SpringJarvis-AI-Powered-Voice-Assistant/blob/main/Images%202/Screenshot%20(611).png) |  

| Mail Integration (Java Mail) | MongoDB Storage |  
|-------------------------------|-----------------|  
| ![Mail Integration](https://github.com/nagenDev/SpringJarvis-AI-Powered-Voice-Assistant/blob/main/Images%202/Screenshot%20(612).png) | ![MongoDB Storage](https://github.com/nagenDev/SpringJarvis-AI-Powered-Voice-Assistant/blob/main/Images%202/Screenshot%20(613).png) |  

📱 **Mail Production Mobile View**  
![Mobile Mail SC](https://github.com/nagenDev/SpringJarvis-AI-Powered-Voice-Assistant/blob/main/Images%202/photo_2025-08-26_09-08-46.jpg)  

---

## 🚀 Tech Stack  

- **Java 17**  
- **Spring Boot 3.x**  
- **Spring AI (Ollama Integration)**  
- **MongoDB Atlas**  
- **ElevenLabs API (TTS + STT)**  
- **Java Mail Sender**  

---

## ⚙️ How It Works  

1. 🎙 **User uploads voice (MP3/WAV)**  
2. 🔊 **Voice-to-Text (STT)** → ElevenLabs transcribes speech  
3. 🧠 **Spring AI + Ollama** → Generates a natural AI response  
4. 🗣 **Text-to-Speech (TTS)** → ElevenLabs converts AI reply into voice  
5. 🎧 **JavaScript frontend plays MP3** in real-time  
6. ⏰ **Alarms trigger AI-generated MP3 messages**  
7. 📩 **Optional Email Alerts** sent to users  

---

<template>
    <div id="mainContainer">
        <h1>My customer account</h1>
        <VBox id="verticalContainer">
            <p id="currentInformation">
                Username: {{ getUsername() }} <br>
                <!-- Password: {{ newPassword }} <br-->
            </p>

            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="Username" v-model="oldUsername"></input>
                <input id="inputBox" type="text" placeholder="New username" v-model="newUsername"></input>
                <button id="mainButton" @click="updateUsername()" style="margin-left: 10px; align-self: center;"><b>Update Username</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="Username" v-model="username"></input>
                <input id="inputBox" type="text" placeholder="Old password" v-model="oldPassword"></input>
                <input id="inputBox" type="text" placeholder="New password" v-model="newPassword"></input>
                <button id="mainButton" @click="updatePassword()" style="margin-left: 10px; align-self: center;"><b>Update Password</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <!--input id="inputBox" type="text" placeholder="Username" v-model="instUsername"></input-->
                <input id="inputBox" type="text" placeholder="Description" v-model="instDescription"></input>
                <input id="inputBox" type="text" placeholder="Picture URL" v-model="instPictURL"></input>
                <button id="mainButton" @click="instructorRequest()" style="margin-left: 10px; align-self: center;">Request to become an instructor</button>
            </VBox>
            <br>

            <HBox id="containerH">
                <button id="subButton" @click="goToInstructorMode()">Instructor mode</button>
                <button id="subButton" @click="goToOwnerMode()">Owner mode</button>
                <!--button id="destroyButton" @click="deleteAccount()">Delete account</button-->
            </HBox>
        </VBox>
    </div>
</template>

<script>
import axios from "axios";
import config from "../../../config";

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'CustomerAccount',
    data() {
        return {
            oldUsername: null,
            newUsername: null,
            username: null,
            oldPassword: null,
            newPassword: null,
            //instUsername: null,
            instDescription: null,
            instPictURL: null
        }
    },
    methods: {
        async updateUsername() {
            try{
                const response = await AXIOS.put('/updateAccountUsername/' + this.oldUsername + '/' + this.newUsername);
                this.$username = this.newUsername;
                console.log(response.data);
                alert("New username updated successfully! It is now: " + this.$username);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        async updatePassword() {
            try{
                const response = await AXIOS.put('/updateAccountPassword/' + this.username + '/' + this.oldPassword + '/' + this.newPassword);
                console.log(response.data);
                alert("New password updated successfully! It is now: " + this.newPassword);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        async instructorRequest() {
            try {
                const response = await AXIOS.post('/createInstructor/' + this.$username + '/' + this.instDescription + '/' + this.instPictURL);
                console.log(response.data);
                alert("Instructor request sent successfully!");
                this.clearInputs();
            } catch(error){
                console.error('Error creating instructor', error.message);
            }
            //lert("Instructor request button clicked");
            //console.log("Instructor request button clicked");
        },
        goToInstructorMode() {
            this.$router.push('/app/account/instructor-account');
        },
        goToOwnerMode() {
            this.$router.push('/app/account/owner-account');
        },
        deleteAccount() {
            alert("Delete account button clicked");
            console.log("Delete account button clicked");
        },
        clearInputs() {
            this.oldUsername = null;
            this.newUsername = null;
            this.username = null;
            this.oldPassword = null;
            this.newPassword = null;
            //this.instUsername = null;
            this.instDescription = null;
            this.instPictURL = null;
        },
            // Methods for global variables
    getAccountType() {
      return localStorage.getItem('accountType');
    },
    setAccountType(accountType) {
      localStorage.setItem('accountType', accountType);
    },
    getUsername() {
      return localStorage.getItem('username');
    },
    setUsername(username) {
      localStorage.setItem('username', username);
    },
    getLoggedIn() {
      return localStorage.getItem('loggedIn') === 'true';
    },
    setLoggedIn(loggedIn) {
      localStorage.setItem('loggedIn', loggedIn);
    },
    getTime() {
      return localStorage.getItem('time');
    },
    setTime(time) {
      localStorage.setItem('time', time);
    },
    getDebuggingMode() {
      return localStorage.getItem('debugging_mode') === 'true';
    },
    setDebuggingMode(debugging_mode) {
      localStorage.setItem('debugging_mode', debugging_mode);
    },
    getLanguage() {
      return localStorage.getItem('language');
    },
    setLanguage(language) {
      localStorage.setItem('language', language);
    },
    getDarkMode() {
      return localStorage.getItem('dark_mode') === 'true';
    },
    setDarkMode(dark_mode) {
      localStorage.setItem('dark_mode', dark_mode);
    },
    }
}
</script>

<style scoped src="../../assets/main.css"> /* OLD COLOR MODE BUTTON: 0e628f */ </style>
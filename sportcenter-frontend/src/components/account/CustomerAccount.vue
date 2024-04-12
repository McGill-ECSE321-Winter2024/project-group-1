<template>
    <div id="mainContainer">
        <h1>My customer account</h1>
        <VBox id="verticalContainer">
            <p id="currentInformation">
                Username: {{ getUsername() }} <br>
                <!-- Password: {{ newPassword }} <br-->
            </p>

            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="New username" v-model="newUsername"></input>
                <button id="mainButton" @click="updateUsername()" style="margin-left: 10px; align-self: center;"><b>Update Username</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="Old password" v-model="oldPassword"></input>
                <input id="inputBox" type="text" placeholder="New password" v-model="newPassword"></input>
                <button id="mainButton" @click="updatePassword()" style="margin-left: 10px; align-self: center;"><b>Update Password</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer" v-if="this.isAnInstructor()">
                <input id="inputBox" type="text" placeholder="Description" v-model="instructorDescription"></input>
                <input id="inputBox" type="text" placeholder="Picture URL" v-model="instructorPictURL"></input>
                <button id="mainButton" @click="instructorRequest()" style="margin-left: 10px; align-self: center;">Request to become an instructor</button>
            </VBox>
            <br>

            <HBox id="containerH">
                <button id="subButton" v-if="this.isAnInstructor()" @click="goToInstructorMode()">Instructor mode</button>
                <button id="subButton" v-if="this.isAnOwner()" @click="goToOwnerMode()">Owner mode</button>
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
                const response = await AXIOS.put('/updateAccountUsername/' + this.getUsername() + '/' + this.newUsername);
                this.username = this.newUsername;
                console.log(response.data);

                if (response.status == 200) {
                  
                  this.setUsername(this.username);
                  alert("New username updated successfully! It is now: " + this.username);
                  this.clearInputs();
                }  else {
                // Handle unsuccessful login
                  console.log('Updating unsucessful');
                }        

            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },

        async updatePassword() {
            try{
                const response = await AXIOS.put('/updateAccountPassword/' + this.getUsername() + '/' + this.oldPassword + '/' + this.newPassword);
                console.log(response.data);


                if (response.status == 200) { 

                  this.setUsername(this.username);
                  alert("New password updated successfully!");
                  this.clearInputs();
                } else {
                // Handle unsuccessful login
                  console.log('Updating password unsucessful');
                } 


            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },

        async instructorRequest() {
            try {

                const response = await AXIOS.post('/createInstructor/' + this.getUsername() + '/' + this.instructorDescription + '/' + this.instructorPictURL);

                if (response.status == 200) { 

                  this.setUsername(this.username);
                  alert("Instructor request sent successfully!");
                  this.clearInputs();

                } else {
                // Handle unsuccessful login
                  console.log('Instructor submission failed');
                } 


            } catch(error){
                console.error('Error creating instructor', error.message);
            }

        },

        async isAnInstructor() {

          try {


            const response = await AXIOS.get('/checkAccountHasInstructorRole/' + this.getAccountId());

            
            if (response.status == 200) {
              return true;
            } else {
              return false;
            }
            

          } catch(error){
              console.error('Error verifying', error.message);
          }          
        
        },

        async goToInstructorMode() { 


            this.$router.push('/app/account/instructor-account');

        },

        async isAnOwner() {

          try {

            const response = await AXIOS.get('/checkAccountHasOwnerRole/' + this.getAccountId());

            if (response.status == 200) {
              return true;
            } else {
              return false;
            }

            } catch(error){
                console.error('Error verifying', error.message);
            }          

          },        

        async goToOwnerMode() {

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
    setAccountId(id) {
      localStorage.setItem('id', id);
    },
    getAccountId() {
      return localStorage.getItem('id');
    },
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
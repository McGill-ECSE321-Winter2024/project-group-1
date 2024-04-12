<template>
    <div id="mainContainer">
        <h1>My instructor account</h1>
        <VBox id="verticalContainer">
            <img id="profilePic" src="@/assets/zyzz.jpg" alt="Instructor" width="200" height="200">
            <br>

            <p id="currentInformation">
                    Current Username: {{ getUsername() }}<br>
                    <!--Description: {{ this.currentDescription }}<br>
                    Picture URL: {{ this.currentPicture }} <br-->
            </p> 
            
            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="New username" v-model="newUsername"></input>
                <button id="mainButton" @click="updateUsername()" style="margin-left: 10px; align-self: center;"><b>Update Username</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="Old password" v-model="oldPassword"></input>
                <input id="inputBox" type="text" placeholder="New password" v-model="newPassword"></input>
                <button id="mainButton" @click="updatePassword()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="Username" v-model="username3"></input>
                <input id="inputBox" type="text" placeholder="New description" v-model="description"></input>
                <input id="inputBox" type="text" placeholder="New image URL" v-model="picture"></input>
                <button id="mainButton" @click="updateInstructorInfo()" style="margin-left: 10px; align-self: center;"><b>Update Instructor Info</b></button>
            </VBox>
            <br>

            <HBox id="horizontalContainer">
                <button id="subButton" v-if="this.isACustomer()" @click="goToCustomerMode()">Customer mode</button>
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
    name: 'InstructorAccount',
    data() {
        return {
            username1: null,
            newUsername: null,
            username2: null,
            oldPassword: null,
            newPassword: null,
            username3: null,
            description: null,
            picture: null
        }
    },
    async getDescription() {
        try{
            const response = await AXIOS.get('/getInstructorByUsername/' + this.$username);
            console.log(response.data);
            this.currentDescription = response.data.description;
        } catch(error){
            console.error('Error creating activity', error.message);
        }
    },
    methods: {
        async updateUsername() {
            try{
                const response = await AXIOS.put('/updateAccountUsername/' + this.getUsername() + '/' + this.newUsername);
                
                if (response.status == 200) {
                  this.$username = this.newUsername;
                  this.setUsername(this.newUsername);
                  alert('Username updated successfully! New username is: ' + this.newUsername);
                  console.log(response.data);
                  this.clearInputs();
                }

            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        async updatePassword() {
            try{
                const response = await AXIOS.put('/updateAccountPassword/' + this.getUsername() + '/' + this.oldPassword + '/' + this.newPassword);
                //console.log(response.data);
                alert("Password updated successfully! New password is: " + this.newPassword);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        async updateInstructorInfo() {
            try{
                const response = await AXIOS.put('/updateInstructor/' + this.getUsername() + '/' + this.description + '/' + this.picture);
                //console.log(response.data);
                alert("Instructor info updated successfully! New description is: " + this.description + " and new image URL is: " + this.picture);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
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

        async isACustomer() {

            try {

            

            const response = await AXIOS.get('/checkAccountHasCustomerRole/' + this.getAccountId());


            if (response.status == 200) {
                return true;
            } else {
                return false;
            }


            } catch(error){
                console.error('Error verifying', error.message);
            }          

        },


        goToCustomerMode() {
            this.$router.push('/app/account/customer-account');
        },
        goToOwnerMode() {
            this.$router.push('/app/account/owner-account');
        },
        async deleteInstructorAccount() {
            try{
                const response = await AXIOS.delete('/deleteInstructorByUsername/' + this.$username);
                console.log(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        clearInputs() {
            this.username1 = null;
            this.newUsername = null;
            this.username2 = null;
            this.oldPassword = null;
            this.newPassword = null;
            this.username3 = null;
            this.description = null;
            this.picture = null;
        },


    setAccountId(id) {
      localStorage.setItem('id', id);
    },
    getAccountId() {
      return localStorage.getItem('id');
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

<style src="../../assets/main.css"></style>
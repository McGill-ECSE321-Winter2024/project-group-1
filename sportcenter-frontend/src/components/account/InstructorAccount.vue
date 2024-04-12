<template>
    <div id="mainContainer">
        <h1>My instructor account</h1>
        <VBox id="verticalContainer">



        
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
                <input id="inputBox" type="text" placeholder="New description" v-model="description"></input>
                <input id="inputBox" type="text" placeholder="New image URL" v-model="picture"></input>
                <p style="margin: 10px; font-weight: bold;" id="currentInformation">Choose a Profile Picture</p>
                
                <VBox style="margin: 10px; font-weight: bold; font-weight: bold;" id="mainContainer">

                    <HBox id="horizontalContainer">
                        <button v-for="(picture, index) in profilePictures" :key="index" @click="selectProfilePicture(index)" class="rounded-button">
                            <img :src="picture.url" alt="Profile Picture" width="100" height="100">
                         </button>
                    </HBox>
                </VBox>    

                <div v-if="selectedProfilePicture !== null" style="margin-top: 20px;">
                    <p style="margin: 10px; font-weight: bold;" id="currentInformation">Profile Picture Selected:</p>
                    <div>
                        <img :src="profilePictures[selectedProfilePicture-1].url" alt="Selected Profile Picture" width="150" height="150" class="rounded-button">
                    </div>
                </div>
                
                <button id="mainButton" @click="updateInstructorInfo()" style="margin-left: 10px; align-self: center; margin:10px;"><b>Update Instructor Info</b></button>
            </VBox>
            <br>

            <HBox id="horizontalContainer">
                <button id="subButton" v-if="isCustomer" @click="goToCustomerMode()">Customer mode</button>
                <button id="subButton" v-if="isOwner" @click="goToOwnerMode()">Owner mode</button>
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

            profilePictures: [
                { url: require('@/assets/ProfilePictures/john.jpg'), number: 1 },
                { url: require('@/assets/ProfilePictures/logo_internet.png'), number: 2 },
                { url: require('@/assets/ProfilePictures/messi.jpg'), number: 3 },
                { url: require('@/assets/ProfilePictures/ronnie.jpeg'), number: 4 },
                { url: require('@/assets/ProfilePictures/zyzz.jpg'), number: 5 },
                // Add more pictures here with their respective URLs and unique numbers
            ],



            selectedProfilePicture: null,
            isCustomer: false,
            isOwner: false,
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


    computed: {
        selectedProfilePictureUrl() {
            if (this.selectedProfilePictureId) {
                const selectedPicture = this.profilePictures.find(picture => picture.number === this.selectedProfilePictureId);
                return selectedPicture ? selectedPicture.url : ''; // Return URL if picture is found, otherwise return empty string
            } else {
                return ''; // Return empty string if no picture is selected
            }
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

        selectProfilePicture(index) {
            this.selectedProfilePicture = this.profilePictures[index].number;
            console.log(this.selectedProfilePicture);
        },

        async showProfilePicture() {

            try {
                const response = await AXIOS.put('/updateAccountUsername/' + this.getUsername() + '/' + this.newUsername);


            } catch(error) {
                console.error('Error finding image', error.message);
                return;   
            }



        },


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
                return;
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
                return;
            }
        },
        async updateInstructorInfo() {
            try{

                

                const response = await AXIOS.put('/updateInstructor/' + this.getUsername() + '/' + this.description + '/' + this.selectedProfilePicture + '/');
                //console.log(response.data);
                alert("Instructor info updated successfully! New description is: " + this.description + " and new image URL is: " + this.selectedProfilePicture);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
                return;
            }
        },

        async checkRoles() {

            this.isCustomer = await this.isACustomer();
            this.isOwner = await this.isAnOwner();
        },


        async isAnOwner() {

            try {

            
            const response = await AXIOS.get('/checkAccountHasOwnerRole/' + this.getAccountId());

            
            if (response.status == 200) {
                return response.data;
            } 
            

            } catch(error){
                console.error('Error verifying', error.message);
                return;
            }          

        },

        async isACustomer() {

            try {

            

            const response = await AXIOS.get('/checkAccountHasCustomerRole/' + this.getAccountId());


            if (response.status == 200) {
                return response.data;
            } 


            } catch(error){
                console.error('Error verifying', error.message);
                return;
            }          

        },


        goToCustomerMode() {

            this.setAccountType('Customer');
            this.$router.push('/app/account/customer-account');
        },

        goToOwnerMode() {
            this.setAccountType('Owner');
            this.$router.push('/app/account/owner-account');
        },
        async deleteInstructorAccount() {
            try{
                const response = await AXIOS.delete('/deleteInstructorByUsername/' + this.$username);
                console.log(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
                return;
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
    },

    created() {
      this.checkRoles();
    }

}
</script>

<style src="../../assets/main.css">



</style>
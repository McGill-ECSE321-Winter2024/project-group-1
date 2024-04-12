<template>
    <div id="mainContainer">
        <h1>My owner account</h1>
        <VBox id="verticalContainer">
            <p id="currentInformation">
                Username: {{ getUsername() }} <br>
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

            <HBox id="containerH">
                <button id="subButton" v-if="this.isACustomer()" @click="goToCustomerMode()">Customer mode</button>
                <button id="subButton" v-if="this.isAnInstructor()" @click="goToInstructorMode()">Instructor mode</button>
                <!--button id="destroyButton" @click="deleteAccount()">Delete account</button-->
            </HBox>
        </VBox>
    </div>
</template>

<script>
import axios from "axios";
import config from "../../../config";
//import { use } from "vue/types/umd";

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
    name: 'OwnerAccount',
    data() {
        return {
            oldUsername: null,
            newUsername: null,
            username: null,
            oldPassword: null,
            newPassword: null
        }
    },
    methods: {
        async updateUsername() {
            try{
                const response = await AXIOS.put('/updateAccountUsername/' + this.getUsername() + '/' + this.newUsername);
                
                if (response.data == 200) {
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
                alert('Password updated successfully!');
                console.log(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },

        async isACustomer() {

          try {

          console.log(this.getAccountId());

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


        async isAnInstructor() {

          try {

            console.log(this.getAccountId());

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




        goToCustomerMode() {
            this.$router.push('/app/account/customer-account');
        },
        goToInstructorMode() {
            this.$router.push('/app/account/instructor-account');
        },
        async deleteAccount() {
            const deletedInstructor = {
                username: this.username
            };
            try{
                const response = await AXIOS.delete('/deleteInstructorByUsername/' + this.username);
                console.log(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        clearInputs() {
            this.oldUsername = null;
            this.newUsername = null;
            this.username = null;
            this.oldPassword = null;
            this.newPassword = null;
        },



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
    }
    }
}
</script>

<style scoped src="../../assets/main.css"></style>
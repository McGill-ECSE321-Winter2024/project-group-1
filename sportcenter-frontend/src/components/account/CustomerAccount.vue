<template>
    <div id="mainContainer">
        <h1>My Customer Account</h1>
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
            <VBox id="verticalContainer" v-if="!isInstructor">
                <input id="inputBox" type="text" placeholder="Description" v-model="instructorDescription"></input>
                <button id="mainButton" @click="instructorRequest()" style="margin-left: 10px; align-self: center;">Request to become an instructor</button>
            </VBox>
            <br>
            <HBox id="containerH">
                <button id="subButton" v-if="isInstructor" @click="goToInstructorMode()">Instructor mode</button>
                <button id="subButton" v-if="isOwner" @click="goToOwnerMode()">Owner mode</button>
            </HBox>
        </VBox>
        <div style="margin-bottom: 40px;" id="mainContainer">
            <p style="margin: 10px; font-weight: bold;" id="currentInformation">Delete Account</p> 
            <p style="margin: 10px; size: 10;" id="currentInformation">Enter your Password to Confirm</p>
            <input id="inputBox" type="text" placeholder="Enter Password for Deletion" v-model="passwordDeletion"></input>
            <button id="destroyButton" @click="deleteCustomer()">Delete Customer</button>
            <button id="destroyButton" @click="deleteAccount()">Delete Account</button>
        </div>
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
            isInstructor: false,
            isOwner: false,
            oldUsername: null,
            newUsername: null,
            username: null,
            oldPassword: null,
            newPassword: null,
            instDescription: null,
            instPictURL: null,
            passwordDeletion: ''
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
                  console.log('Updating unsucessful');
                }        
            } catch(error){
                console.error('Error creating activity', error.message);
                return;
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
                  console.log('Updating password unsucessful');
                } 
            } catch(error){
                console.error('Error creating activity', error.message);
                return;
            }
        },
        async instructorRequest() {
            try {
                const response = await AXIOS.post('/createInstructor/' + this.getUsername() + '/' + this.instructorDescription + '/' + '2');
                if (response.status == 200) { 
                  this.setUsername(this.username);
                  alert("Instructor request sent successfully!");
                  this.clearInputs();
                } else {
                  console.log('Instructor submission failed');
                } 
            } catch(error){
                console.error('Error creating instructor', error.message);
                return;
            }
        },

        async checkRoles() {
          this.isInstructor = await this.isAnInstructor();
          this.isOwner = await this.isAnOwner();
        },

        async isAnInstructor() {
          try {
            const response = await AXIOS.get('/checkAccountHasInstructorRole/' + this.getAccountId());
            if (response.status == 200) {
              return response.data;
            } 
          } catch(error){
              console.error('Error verifying', error.message);
              return;
          }          
        },

        async goToInstructorMode() { 
            this.setAccountType('Instructor');
            this.$router.push('/app/account/instructor-account');
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
        async goToOwnerMode() {
            this.setAccountType('Owner');
            this.$router.push('/app/account/owner-account');
        },
        async deleteCustomer() {
          try {
              const response = await AXIOS.get('/getCustomerByUsername/' + this.getUsername() + '/');
              if (response.status == 200) {
                  if (response.data.account.password == this.passwordDeletion) {
                      try {
                          const responseDelete = await AXIOS.delete('/deleteCustomerByUsername/' + this.getUsername()+ '/');
                          if (responseDelete.status == 200) {
                              this.setLoggedIn(false);
                              this.setAccountType('Guest');
                              this.setUsername('');
                              this.setAccountId('');
                              this.$router.push('/app/auth/login');
                          }
                      } catch(error) {
                          console.error('deletion went wrong', error.message);
                          return;
                      }
                  } 
              }
          } catch(error) {
              console.error('Cannot check password', error.message);
              return;
          }
        },

        async deleteAccount() {
          try {
              const response = await AXIOS.get('/getAccountByUsername/' + this.getUsername());

              const ownerResponse = await AXIOS.get('/checkAccountHasOwnerRole/' + this.getAccountId());

              if (ownerResponse.data) {
                alert("Cannot delete Owner Role!");
                return;
              }
              
              if (response.status == 200) {
                  if (response.data.password == this.passwordDeletion) {
                      try {
                          const responseDelete = await AXIOS.delete('/deleteAccount/' + this.getAccountId() + '/');
                          if (responseDelete.status == 200) {
                              this.setLoggedIn(false);
                              this.setAccountType('Guest');
                              this.setUsername('');
                              this.setAccountId('');
                              this.$router.push('/app/auth/login');
                          }
                      } catch(error) {
                          console.error('deletion went wrong', error.message);
                          return;
                      }
                  } 
              }
          } catch(error) {
              console.error('Cannot check password', error.message);
              return;
          }
        },


        clearInputs() {
            this.oldUsername = null;
            this.newUsername = null;
            this.username = null;
            this.oldPassword = null;
            this.newPassword = null;
            this.instDescription = null;
            this.instPictURL = null;
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
    },
    },

    created() {
      this.checkRoles();
    }
}
</script>

<style scoped src="../../assets/main.css"></style>
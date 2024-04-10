<template>
    <div id="mainContainer">
        <h1>My owner account</h1>
        <VBox id="verticalContainer">
            <p id="currentInformation">
                Username: Joe Mama<br>
                Email: joe@gym.com
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

            <HBox id="containerH">
                <button id="subButton" @click="goToCustomerMode()">Customer mode</button>
                <button id="subButton" @click="goToInstructorMode()">Instructor mode</button>
                <button id="destroyButton" @click="deleteAccount()">Delete account</button>
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
            accounts: [],
            oldUsername: null,
            newUsername: null,
            username: null,
            oldPassword: null,
            newPassword: null
        }
    },
    methods: {
        async updateUsername() {
            const newUsername = {
                oldUsername: this.oldUsername,
                newUsername: this.newUsername
            };
            try{
                const response = await AXIOS.put('/updateAccountUsername/' + this.oldUsername + '/' + this.newUsername);
                this.accounts.push(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        async updatePassword() {
            const newPassword = {
                username: this.username,
                newPassword: this.newPassword,
                oldPassword: this.oldPassword
            };
            try{
                const response = await AXIOS.put('/updateAccountPassword/' + this.username + '/' + this.oldPassword + '/' + this.newPassword);
                this.accounts.push(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
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
                this.accounts.push(response.data);
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
        }
    }
}
</script>

<style scoped src="../../assets/main.css"></style>
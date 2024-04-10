<template>
    <div id="mainContainer">
        <h1>My customer account</h1>
        <VBox id="verticalContainer">
            <p id="currentInformation">
                Username: {{ $username }} <br>
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

            <button id="subButton" @click="instructorRequest()" style="margin-left: 10px; align-self: center;">Request to become an instructor</button>
            <br>

            <HBox id="containerH">
                <button id="subButton" @click="goToInstructorMode()">Instructor mode</button>
                <button id="subButton" @click="goToOwnerMode()">Owner mode</button>
                <button id="destroyButton" @click="deleteAccount()">Delete account</button>
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
            newPassword: null
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
        instructorRequest() {
            alert("Instructor request button clicked");
            console.log("Instructor request button clicked");
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
        }
    }
}
</script>

<style scoped src="../../assets/main.css"> /* OLD COLOR MODE BUTTON: 0e628f */ </style>
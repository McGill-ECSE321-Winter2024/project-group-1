<template>
    <div id="mainContainer">
        <h1>My instructor account</h1>
        <VBox id="verticalContainer">
            <img id="profilePic" src="@/assets/zyzz.jpg" alt="Instructor" width="200" height="200">
            <br>

            <p id="currentInformation">
                    Username: Joe Mama<br>
                    Email: joe@gym.com<br>
                    Description: Expert in goat yoga, certified in pilates and zumba, all the moms love me, I'm the best instructor in the world (I started a cult)
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
                <button id="mainButton" @click="updatePassword()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="Username" v-model="username2"></input>
                <input id="inputBox" type="text" placeholder="New description" v-model="description"></input>
                <input id="inputBox" type="text" placeholder="New image URL" v-model="picture"></input>
                <button id="mainButton" @click="updateInstructorInfo()" style="margin-left: 10px; align-self: center;"><b>Update Instructor Info</b></button>
            </VBox>
            <br>

            <HBox id="horizontalContainer">
                <button id="subButton" @click="goToCustomerMode()">Customer mode</button>
                <button id="subButton" @click="goToOwnerMode()">Owner mode</button>
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
    name: 'InstructorAccount',
    data() {
        return {
            accounts: [],
            oldUsername: null,
            newUsername: null,
            username: null,
            oldPassword: null,
            newPassword: null,
            username2: null,
            description: null,
            picture: null
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
        async updateInstructorInfo() {
            const newInstructor = {
                username2: this.username2,
                description: this.description,
                picture: this.picture
            };
            try{
                const response = await AXIOS.put('/updateInstructor/' + this.username2 + '/' + this.description + '/' + this.picture);
                this.accounts.push(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        goToCustomerMode() {
            this.$router.push('/app/account/customer-account');
        },
        goToOwnerMode() {
            this.$router.push('/app/account/owner-account');
        },
        async deleteInstructorAccount() {
            const deletedInstructor = {
                username2: this.username2
            };
            try{
                const response = await AXIOS.delete('/deleteInstructorByUsername/' + this.username2);
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
            this.username2 = null;
            this.description = null;
            this.picture = null;
        }
    }
}
</script>

<style src="../../assets/main.css"></style>
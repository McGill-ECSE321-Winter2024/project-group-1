<template>
    <div id="mainContainer">
        <h1>Login</h1>
        <VBox id="verticalContainer">
            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="Username" v-model="username"></input>
                <!--<input id="inputBox" type="password" placeholder="Password"></input>-->
                <!--<input id="inputBox" type="text" placeholder="Status" v-model="status"> --><!-- This is not the correct type it should be InstructorStatus-->
                <input id="inputBox" type="text" placeholder="Description (for Instructor)" v-model="description">
                <input id="inputBox" type="text" placeholder="ProfilePicURL (for Instructor)" v-model="profilePicURL">
                <!--TODO: ADD ACCOUNT TYPE LOGIN CHOICE-->
                <HBox id="horizontalContainer">
                    <button id="mainButton" @click="loginCustomer()"><b>Login as customer</b></button>
                    <button id="mainButton" @click="loginInstructor()"><b>Login as instructor</b></button>
                    <button id="mainButton" @click="loginOwner()"><b>Login as owner</b></button>
                </HBox>
            </VBox>
            <br>
            <VBox id="verticalContainer" style="width: 40%; align-self: center;">
                <v-divider>Other options</v-divider>
                <button id="subButton" @click="goToForgotPassword()"><b>Forgot password</b></button>
                <button id="subButton" @click="goToCreateAccount()"><b>Create a new account</b></button>
            </VBox>
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
    name: 'Login',
    data() {
    return {
        customers: [],
        instructors: [],
        owners: [],
        username: null,
        //status: null,
        description: null,
        profilePicURL: null
    }
},
    methods: {
        async loginCustomer() {
            //alert("Create account button clicked");
            //console.log("Create account button clicked");
            const newCustomer = {
                username: this.username
            };
            try{
                const response = await AXIOS.post('/createCustomer/' + this.username);
                this.customers.push(response.data);
                this.clearInputs();
                //this.accounts = response.data.accounts; // or response.data
            } catch(error){
                console.error('Error fetching accounts', error.message);
            }
        },
        async loginInstructor() {
            //alert("Create account button clicked");
            //console.log("Create account button clicked");
            const newInstructor = {
                username: this.username,
                //status: this.status,
                description: this.description,
                profilePicURL: this.profilePicURL
            };
            try{
                const response = await AXIOS.post('/createInstructor/' + this.username + '/' + this.description + '/' + this.profilePicURL);
                this.instructors.push(response.data);
                this.clearInputs();
                //this.accounts = response.data.accounts; // or response.data
            } catch(error){
                console.error('Error fetching accounts', error.message);
            }
        },
        async loginOwner() {
            //alert("Create account button clicked");
            //console.log("Create account button clicked");
            const newOwner = {
                username: this.username
            };
            try{
                const response = await AXIOS.post('/createOwner/' + this.username);
                this.owners.push(response.data);
                this.clearInputs();
                //this.accounts = response.data.accounts; // or response.data
            } catch(error){
                console.error('Error fetching accounts', error.message);
            }
        },
        goToForgotPassword() {
            this.$router.push('/app/auth/forgotpassword');
        },
        goToCreateAccount() {
            this.$router.push('/app/auth/createaccount');
        },
        clearInputs() {
            this.username = null;
        }
    }
}
</script>

<style scoped src="../../assets/main.css"></style>
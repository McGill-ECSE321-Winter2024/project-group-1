<template>
    <div id="mainContainer">
        <h1>Login</h1>
        <VBox id="verticalContainer">
            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="Username" v-model="username"></input>
                <input id="inputBox" type="password" placeholder="Password" v-model="password"></input>
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
                <!--button id="subButton" @click="goToCreateAccount()"><b>Create a new account</b></button-->
                <button id="subButton" @click="goToContinueAsGuest()">Continue as a guest</button>
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
    name: "Login",
    data () {
        return {
            username: null,
            password: null
        };
    },
    methods: {
        async loginCustomer() {
            try{
                const response = await AXIOS.get('/login/' + this.username + '/' + this.password + '/customer');
                console.log(response.data);
                // Set user to logged in
                this.$loggedIn = true;
                this.$accountType = 'Customer';
                this.$username = this.username;
                this.clearInputs();
                this.$router.push('/');
            } catch(error){
                alert(error.message);
            }
        },
        async loginInstructor() {
            try{
                const response = await AXIOS.get('/login/' + this.username + '/' + this.password + '/instructor');
                console.log(response.data);
                // Set user to logged in
                this.$loggedIn = true;
                this.$accountType = 'Instructor';
                this.$username = this.username;
                this.clearInputs();
                this.$router.push('/');
            } catch(error){
                alert(error.message);
            }
        },
        async loginOwner() {
            try{
                const response = await AXIOS.get('/login/' + this.username + '/' + this.password + '/owner');
                console.log(response.data);
                // Set user to logged in
                this.$loggedIn = true;
                this.$accountType = 'Owner';
                this.$username = this.username;
                this.clearInputs();
                this.$router.push('/');
            } catch(error){
                alert(error.message);
            }
        },
        goToForgotPassword() {
            this.$router.push('/app/auth/forgotpassword');
        },
        goToCreateAccount() {
            this.$router.push('/app/auth/createaccount');
        },
        goToContinueAsGuest() {
            this.$accountType = 'Guest';
            this.$username = 'JoeMama';
            this.$loggedIn = false;
            this.$router.push('/');
        },
        clearInputs() {
            this.username = null;
            this.password = null;
        }
    }
};

</script>

<style scoped src="../../assets/main.css"></style>
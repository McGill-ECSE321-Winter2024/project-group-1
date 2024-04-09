<template>
    <div id="mainContainer">
        <h1>Login</h1>
        <VBox id="verticalContainer">
            <VBox id="verticalContainer">
                <input id="inputBox" type="username" placeholder="Username"></input>
                <input id="inputBox" type="password" placeholder="Password"></input>
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
    export default {
        name: 'Login',
        methods: {
            loginCustomer() {
                alert("Login button clicked");
                var currentDate = new Date();
                console.log("Login button clicked: " + currentDate);
            },
            loginInstructor() {
                alert("Login button clicked");
                var currentDate = new Date();
                console.log("Login button clicked: " + currentDate);
            },
            loginOwner() {
                alert("Login button clicked");
                var currentDate = new Date();
                console.log("Login button clicked: " + currentDate);
            },
            goToForgotPassword() {
                this.$router.push('/app/auth/forgotpassword');
            },
            goToCreateAccount() {
                this.$router.push('/app/auth/createaccount');
            }
        }
    }
</script>

<style scoped src="../../assets/main.css"></style>
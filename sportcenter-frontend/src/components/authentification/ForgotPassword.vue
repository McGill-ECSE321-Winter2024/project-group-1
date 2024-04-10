<template>
    <div id="mainContainer">
        <h1><b>Password reset</b></h1>
        <VBox id="verticalContainer">
            <VBox id="verticalContainer">
                <input id="inputBox" type="username" placeholder="Existing username"></input>
                <input id="inputBox" type="password" placeholder="New password"></input>
                <button id="mainButton" @click="forgotPassword()"><b>Reset my password</b></button>
            </VBox>
            <br>
            <VBox id="verticalContainer" style="width: 40%; align-self: center;">
                <v-divider>Other options</v-divider>
                <button id="subButton" @click="goToLogin()"><b>I already have an account</b></button>
                <button id="subButton" @click="goToCreateAccount()"><b>Create a new account</b></button>
                <button id="subButton" @click="goToContinueAsGuest()">Continue as a guest</button>
            </VBox>
        </VBox>
    </div>
</template>

<script scoped>
    export default {
        name: 'ForgotPassword',
        methods: {
            forgotPassword() {
                alert("Forgot Password button clicked");
                console.log("Forgot Password button clicked");
            },
            goToCreateAccount() {
                this.$router.push('/app/auth/createaccount');
            },
            goToLogin() {
                this.$router.push('/app/auth/login');
            },
            goToContinueAsGuest() {
                this.$accountType = 'Guest';
                this.$router.push('/');
            }
        }
    }
</script>

<style scoped src="../../assets/main.css"></style>
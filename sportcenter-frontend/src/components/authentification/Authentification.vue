<template>
    <div id="mainContainer">
        <h1>Authentification menu</h1>
        <VBox id="verticalContainer">
            <button id="mainButton" @click="goToLogin()">Login</button>
            <button id="mainButton" @click="goToForgotPassword()">Forgot password</button>
            <button id="mainButton" @click="goToCreateAccount()">Create a new account</button>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'Authentification',
        methods: {
            goToLogin() {
                this.$router.push('/app/auth/login');
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

<style scoped src="../../assets/main.css">
<template>
    <div id="mainContainer">
        <h1>Create a new account</h1>
        <VBox id="verticalContainer">
            <VBox id="verticalContainer">
                <input id="inputBox" type="username" placeholder="Username"></input>
                <input id="inputBox" type="password" placeholder="Password"></input>
                <button id="mainButton" @click="createAccount()"><b>Create account</b></button>
            </VBox>
            <br>
            <VBox id="verticalContainer" style="width: 40%; align-self: center;">
                <v-divider>Other options</v-divider>
                <button id="subButton" @click="goToLogin()"><b>I already have an account</b></button>
            </VBox>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'CreateAccount',
        methods: {
            createAccount() {
                alert("Create account button clicked");
                console.log("Create account button clicked");
            },
            goToLogin() {
                this.$router.push('/app/auth/login');
            }
        },
    }
</script>

<style scoped src="../../assets/main.css"></style>
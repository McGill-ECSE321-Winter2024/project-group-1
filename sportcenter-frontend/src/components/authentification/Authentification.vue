<template>
    <div id="authentification">
        <h1><b>Authentification menu</b></h1>
        <VBox id="box">
            <button id="sectionTwoButton" @click="goToLogin()">Login</button>
            <button id="sectionTwoButton" @click="goToForgotPassword()">Forgot password</button>
            <button id="sectionTwoButton" @click="goToCreateAccount()">Create a new account</button>
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

<style>
    #authentification{
        font-family: 'Avenir', Helvetica, Arial sans-serif;
        margin: 60px;
        border-radius: 30px;
        padding: 40px;
        box-shadow: 0 50px 50px 0 rgba(209, 184, 52, 0.2);
        color: #2c3e50;
        background: #e4e3e394;
        align-items: center;
        width: 85%;
        max-width: 800px;
        max-height: fit-content;
        text-align: center;
    }

    #box {
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    #sectionOneButton {
        background-color: #d1b834;
        border: none;
        color: white;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 12px;
        padding: 10px 24px;
    }

    #sectionTwoButton {
        background-color: #6d8517;
        border: none;
        color: white;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 12px;
        padding: 10px 24px;
    }
</style>
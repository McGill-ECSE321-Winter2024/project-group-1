<template>
    <div id="forgotPasswordPage">
        <h1><b>Password reset</b></h1>
        <VBox id="containerV">
            <VBox id="containerV">
                <input id="inputBox" type="username" placeholder="Existing username"></input>
                <input id="inputBox" type="password" placeholder="New password"></input>
                <button id="mainButton" @click="forgotPassword()"><b>Reset my password</b></button>
            </VBox>
            <br>
            <v-divider>Other options</v-divider>
            <HBox id="containerH">
                <button id="optionButton" @click="goToLogin()"><b>I already have an account</b></button>
                <button id="optionButton" @click="goToCreateAccount()"><b>Create a new account</b></button>
            </HBox>
        </VBox>
    </div>
</template>

<script>
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
            }
        }
    }
</script>

<style>
    ::placeholder {
        text-align: center;
    }

    #forgotPasswordPage {
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

    #containerV {
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    #containerH {
        display: flex;
        flex-direction: row;
        justify-content: center;
    }

    #inputBox {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        box-sizing: border-box;
        border-radius: 30px;
        text-align: center;
    }

    #mainButton {
        background-color: #3f5b0b;
        color: white;
        border: none;
        border-radius: 5px;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 30px;
        width: 100%;
    }

    #optionButton {
        background-color: #6D8517;
        color: rgb(255, 255, 255);
        border: none;
        border-radius: 5px;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 30px;
        margin-inline: 10px;
    }
</style>
<template>
    <div id="LoginPage">
        <br>
        <h1><b>Register</b></h1>
        <VBox id="containerMain">
            <input id="inputBox" type="newUsername" placeholder="New username"></input>
            <input id="inputBox" type="newPassword" placeholder="New password"></input>

            <button id="registerButton" @click="register()">Register</button>
            
            <HBox id="containerSub">
                <button id="loginButton" @click="login()">Login menu</button>
            </HBox>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'LoginPage',
        methods: {
            register() {
                alert("Register button clicked");
            },
            login() {
                alert("Login button clicked");
            }
        }
    }
</script>

<style>
    ::placeholder {
        text-align: center;
    }

    #LoginPage {
        font-family: 'Avenir', Helvetica, Arial sans-serif;
        margin: 60px;
        border-radius: 30px;
        padding: 30px;
        box-shadow: 0 50px 50px 0 rgba(0, 0, 0, 0.2);
        color: #2c3e50;
        background: #e4e3e394;
    }

    #containerMain {
        max-height: fit-content;
        display: flex;
        flex-direction: column;
        align-items: top;
        justify-content: center;
        height: 100vh;
        margin: 60px;
    }

    #containerSub {
        display: flex;
        flex-direction: row;
        align-items: center;
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

    #registerButton {
        background-color: #4CAF50;
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
        margin: 20px 0;
    }

        #loginButton {
        background-color: #3989de;
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
        margin-left: 20px;
    }
</style>
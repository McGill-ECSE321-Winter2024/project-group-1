<template>
    <div id="InstructorAccount">
        <h1><b>My instructor account</b></h1>
        <VBox id="containerV">
            <img id="profilePic" src="@/assets/instructor.jpg" alt="Instructor" width="200" height="200">
            <br>
            <p id="currentInformation">
                    Username: Joe Mama<br>
                    Email: joe@gym.com<br>
                    Description: Expert in goat yoga, certified in pilates and zumba, all the moms love me, I'm the best instructor in the world (I started a cult)
            </p>
            
            <HBox id="containerH">
                
                <VBox>
                    <input id="inputBox" type="username" placeholder="New username"></input>
                    <input id="inputBox" type="confirmUsername" placeholder="Confirm new username"></input>
                </VBox>
                <button id="updateButton" @click="updateUsername()"><b>Update</b></button>
            </HBox>
            <HBox id="containerH">
                <VBox>
                    <input id="inputBox" type="oldPassword" placeholder="Old password"></input>
                    <input id="inputBox" type="newPassword" placeholder="New password"></input>
                    <input id="inputBox" type="confirmNewPassword" placeholder="Confirm new password"></input>
                </VBox>
                <button id="updateButton" @click="updatePassword()"><b>Update</b></button>
            </HBox>
            <HBox id="containerH">
                <VBox>
                    <input id="inputBox" type="newImageURL" placeholder="New image URL"></input>
                    <input id="inputBox" type="confirmNewImageURL" placeholder="Confirm new image URL"></input>
                </VBox>
                <button id="updateButton" @click="updateImageURL()"><b>Update</b></button>
            </HBox>
            
            <HBox>
                <VBox>
                    <input id="inputBox" type="newDescription" placeholder="New description" style="width: 620px;"></input>
                </br>
                </VBox>
                <button id="updateButton" @click="updateDescription()"><b>Update</b></button>
            </HBox>
            <br>
            <HBox id="containerH">
                <button id="modeButton" @click="goToCustomerMode()"><b>Customer mode</b></button>
                <button id="modeButton" @click="goToOwnerMode()"><b>Owner mode</b></button>
                <button id="deleteButton" @click="deleteAccount()"><b>Delete account</b></button>
            </HBox>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'InstructorAccount',
        methods: {
            updateUsername() {
                alert("Update button clicked");
                console.log("Update button clicked");
            },
            updatePassword() {
                alert("Update button clicked");
                console.log("Update button clicked");
            },
            updateImageURL() {
                alert("Update button clicked");
                console.log("Update button clicked");
            },
            updateDescription() {
                alert("Update button clicked");
                console.log("Update button clicked");
            },
            goToCustomerMode() {
                this.$router.push('/app/account/customer-account');
            },
            goToOwnerMode() {
                this.$router.push('/app/account/owner-account');
            },
            deleteAccount() {
                alert("Delete account button clicked");
                console.log("Delete account button clicked");
            }
        }
    }
</script>

<style>
    ::placeholder {
        text-align: center;
    }

    #InstructorAccount {
        font-family: 'Avenir', Helvetica, Arial sans-serif;
        margin: 60px;
        border-radius: 30px;
        padding: 40px;
        box-shadow: 0 50px 50px 0 rgba(209, 184, 52, 0.2);
        color: #2c3e50;
        background: #e4e3e394;
        width: 800px;
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
        align-content: center;
        margin-bottom: 30px;
    }

    #currentInformation {
        margin-inline: 10px;
        vertical-align: center;
        text-align: center;
        font-size: 20px;
    }

    #profilePic {
        border-radius: 50%;
        margin-inline: 10px;
        align-self: center;
    }

    #inputBox {
        width: 500px;
        height: 50px;
        padding: 12px 20px;
        margin: 4px 2px;
        display: inline-block;
        border: 1px solid #ccc;
        box-sizing: border-box;
        border-radius: 30px;
        text-align: center;
    }

    #updateButton {
        background-color: #3f5b0b;
        height: 50px;
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
        align-self: center;
    }

    #modeButton {
        background-color: #0e628f;
        height: 50px;
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
        align-self: center;
    }

    #deleteButton {
        background-color: #ff0000;
        height: 50px;
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
        align-self: center;
    }
</style>
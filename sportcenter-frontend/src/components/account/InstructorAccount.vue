<template>
    <div id="mainContainer">
        <h1>My instructor account</h1>
        <VBox id="verticalContainer">
            <img id="profilePic" src="@/assets/zyzz.jpg" alt="Instructor" width="200" height="200">
            <br>

            <p id="currentInformation">
                    Username: Joe Mama<br>
                    Email: joe@gym.com<br>
                    Description: Expert in goat yoga, certified in pilates and zumba, all the moms love me, I'm the best instructor in the world (I started a cult)
            </p>
            
            <VBox id="verticalContainer">
                <input id="inputBox" type="username" placeholder="New username"></input>
                <input id="inputBox" type="username" placeholder="Confirm new username"></input>
                <button id="mainButton" @click="updateUsername()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="oldPassword" placeholder="Old password"></input>
                <input id="inputBox" type="newPassword" placeholder="New password"></input>
                <input id="inputBox" type="confirmNewPassword" placeholder="Confirm new password"></input>
                <button id="mainButton" @click="updatePassword()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="newImageURL" placeholder="New image URL"></input>
                <input id="inputBox" type="confirmNewImageURL" placeholder="Confirm new image URL"></input>
                <button id="mainButton" @click="updateImageURL()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="newDescription" placeholder="New description"></input>
                <button id="mainButton" @click="updateDescription()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <HBox id="horizontalContainer">
                <button id="subButton" @click="goToCustomerMode()">Customer mode</button>
                <button id="subButton" @click="goToOwnerMode()">Owner mode</button>
                <button id="destroyButton" @click="deleteAccount()">Delete account</button>
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

<style src="../../assets/main.css"></style>
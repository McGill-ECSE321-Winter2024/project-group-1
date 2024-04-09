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
                <input id="inputBox" type="username" placeholder="New username" style="width: 100%;"></input>
                <input id="inputBox" type="username" placeholder="Confirm new username" style="width: 100%;"></input>
                <button id="mainButton" @click="updateUsername()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="oldPassword" placeholder="Old password" style="width: 100%;"></input>
                <input id="inputBox" type="newPassword" placeholder="New password" style="width: 100%;"></input>
                <input id="inputBox" type="confirmNewPassword" placeholder="Confirm new password" style="width: 100%;"></input>
                <button id="mainButton" @click="updatePassword()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="newImageURL" placeholder="New image URL" style="width: 100%;"></input>
                <input id="inputBox" type="confirmNewImageURL" placeholder="Confirm new image URL" style="width: 100%;"></input>
                <button id="mainButton" @click="updateImageURL()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <VBox id="verticalContainer">
                <input id="inputBox" type="newDescription" placeholder="New description" style="width: 100%;"></input>
                <button id="mainButton" @click="updateDescription()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </VBox>
            <br>

            <HBox id="horizontalContainer">
                <button id="subButton" @click="goToCustomerMode()"><b>Customer mode</b></button>
                <button id="subButton" @click="goToOwnerMode()"><b>Owner mode</b></button>
                <button id="destroyButton" @click="deleteAccount()"><b>Delete account</b></button>
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

<style scoped>
    #profilePic {
        border-radius: 50%;
        margin-inline: 10px;
        align-self: center;
    }
</style>

<style src="../../assets/main.css"></style>
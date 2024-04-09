<template>
    <div id="mainContainer">
        <h1>My owner account</h1>
        <VBox id="verticalContainer">
            <p id="currentInformation">
                Username: Joe Mama<br>
                Email: joe@gym.com
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

            <HBox id="containerH">
                <button id="subButton" @click="goToCustomerMode()"><b>Customer mode</b></button>
                <button id="subButton" @click="goToInstructorMode()"><b>Instructor mode</b></button>
                <button id="destroyButton" @click="deleteAccount()"><b>Delete account</b></button>
            </HBox>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'OwnerAccount',
        methods: {
            updateUsername() {
                alert("Update button clicked");
                console.log("Update button clicked");
            },
            updatePassword() {
                alert("Update button clicked");
                console.log("Update button clicked");
            },
            goToCustomerMode() {
                this.$router.push('/app/account/customer-account');
            },
            goToInstructorMode() {
                this.$router.push('/app/account/instructor-account');
            },
            deleteAccount() {
                alert("Delete account button clicked");
                console.log("Delete account button clicked");
            }
        }
    }
</script>

<style>
    #currentInformation {
        margin-inline: 10px;
        vertical-align: center;
        text-align: center;
        font-size: 20px;
    }
</style>

<style scoped src="../../assets/main.css"></style>
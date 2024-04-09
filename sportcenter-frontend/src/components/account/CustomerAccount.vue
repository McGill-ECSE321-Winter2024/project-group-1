<template>
    <div id="mainContainer">
        <h1>My customer account</h1>
        <VBox id="verticalContainer">
            <p id="currentInformation">
                Username: Joe Mama<br>
                Email: joe@gym.com
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

            <HBox id="containerH">
                <button id="subButton" @click="goToInstructorMode()"><b>Instructor mode</b></button>
                <button id="subButton" @click="goToOwnerMode()"><b>Owner mode</b></button>
                <button id="destroyButton" @click="deleteAccount()"><b>Delete account</b></button>
            </HBox>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'CustomerAccount',
        methods: {
            updateUsername() {
                alert("Update button clicked");
                console.log("Update button clicked");
            },
            updatePassword() {
                alert("Update button clicked");
                console.log("Update button clicked");
            },
            goToInstructorMode() {
                this.$router.push('/app/account/instructor-account');
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

<style scoped src="../../assets/main.css"> /* OLD COLOR MODE BUTTON: 0e628f */ </style>
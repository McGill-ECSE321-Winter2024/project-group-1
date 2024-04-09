<template>
    <div id="mainContainer">
        <h1>My customer account</h1>
        <VBox id="verticalContainer">
            <p id="currentInformation">
                Username: Joe Mama<br>
                Email: joe@gym.com
            </p>

            <HBox id="horizontalContainer">
                <VBox id="verticalContainer">
                    <input id="inputBox" type="username" placeholder="New username" style="width: 500px;"></input>
                    <input id="inputBox" type="username" placeholder="Confirm new username" style="width: 500px;"></input>
                </VBox>
                <button id="mainButton" @click="updateUsername()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </HBox>
            <br>

            <HBox id="horizontalContainer">
                <VBox id="verticalContainer">
                    <input id="inputBox" type="oldPassword" placeholder="Old password" style="width: 500px;"></input>
                    <input id="inputBox" type="newPassword" placeholder="New password" style="width: 500px;"></input>
                    <input id="inputBox" type="confirmNewPassword" placeholder="Confirm new password" style="width: 500px;"></input>
                </VBox>
                <button id="mainButton" @click="updatePassword()" style="margin-left: 10px; align-self: center;"><b>Update</b></button>
            </HBox>
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

<style>
    #currentInformation {
        margin-inline: 10px;
        vertical-align: center;
        text-align: center;
        font-size: 20px;
    }
</style>

<style scoped src="../../assets/main.css"> /* OLD COLOR MODE BUTTON: 0e628f */ </style>
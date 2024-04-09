<template>
    <div id="mainContainer">
        <h1>Account menu</h1>
        <VBox id="verticalContainer">
            <button id="mainButton" @click="goToCustomerMode()">Customer mode</button>
            <button id="mainButton" @click="goToInstructorMode()">Instructor mode</button>
            <button id="mainButton" @click="goToOwnerMode()">Owner mode</button>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'Account',
        methods: {
            goToCustomerMode() {
                this.$router.push('/app/account/customer-account');
            },
            goToInstructorMode() {
                this.$router.push('/app/account/instructor-account');
            },
            goToOwnerMode() {
                this.$router.push('/app/account/owner-account');
            }
        }
    }
</script>

<style scoped src="../../assets/main.css">
<template>
    <div id="account">
        <h1><b>Account menu</b></h1>
        <VBox id="box">
            <button id="sectionTwoButton" @click="goToCustomerMode()">Customer mode</button>
            <button id="sectionTwoButton" @click="goToInstructorMode()">Instructor mode</button>
            <button id="sectionTwoButton" @click="goToOwnerMode()">Owner mode</button>
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

<style scoped>
    #account{
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
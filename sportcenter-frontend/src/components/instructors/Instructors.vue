<template>
    <div id="mainContainer">
        <h1>Instructors management menu</h1>
        <VBox id="verticalContainer">
            <button id="mainButton" @click="goToViewInstructors()">View instructors</button>
            <button id="mainButton" @click="goToManageInstructors()">Manage instructors</button>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'Authentification',
        methods: {
            goToViewInstructors() {
                this.$router.push('/app/instructors/view-instructors');
            },
            goToManageInstructors() {
                this.$router.push('/app/instructors/manage-instructors');
            }
        }
    }
</script>

<style scoped src="../../assets/main.css">
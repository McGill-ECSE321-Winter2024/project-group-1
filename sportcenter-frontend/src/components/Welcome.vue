<template>
    <div style="width:1200px;">
        <h1>Welcome</h1>
        <h2>Put yourself first</h2>
        <button @click="goHome()" v-if="$loggedIn">Home</button>
    </div>
</template>

<script>
    export default {
        name: 'HomePage',
    methods: {
            goHome() {
                this.$router.push('/app/home');
            }
        }
    }
</script>

<style scoped src="../assets/main.css"></style>
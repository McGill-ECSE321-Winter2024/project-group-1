<template>
  <div class="app">
    <header>
      <Navbar />
      <router-view>
      </router-view>
    </header>
  </div>

</template>

<script>
import Navbar from './components/Navbar';
import Hello from './components/Hello';

export default {
  name: 'app',
  components: {
    Navbar
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'montserrat', sans-serif;
}

.app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

header {
  background-color: #333;
  color: #FFF;
  padding: 10px 0;
}


nav {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
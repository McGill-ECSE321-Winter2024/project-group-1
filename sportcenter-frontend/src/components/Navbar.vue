<template>
  <nav>
    <img src="@/assets/SC.png" alt=""/>
    <nav class="text">
      <div class="menu-item"><a href="#">Home</a></div>
      <div class="menu-item"><a href="#">About</a></div>
      <Dropdown title="Services" :items="services" />
      <div class="menu-item"><a href="#">Contact</a></div>
    </nav>
    </nav>
</template>

<script>
import Dropdown from './Dropdown';

export default {
  name: 'navbar',
  components: {
    Dropdown
  },
  data () {
    return {
      services: [
        {
          title: 'Web',
          link: '#'
        },
        {
          title: 'Design',
          link:'#'
        },
        {
          title: 'Videos',
          link: '#'
        }
        
      ]
    }
  }
}
</script>

<style>
.text{
  display: flex;
  text-align: left;
  position: middle;
}

.logo{
  display: absolute;  
  align-items: normal;
  position: left;
}
.menu-container {
  display: flex;
  align-items: center; /* Center menu items vertically */
}
nav .menu-item {
  color: #FF5858;
  padding: 10px 20px;
  position: relative;
  text-align: center;
  border-bottom: 3px solid transparent;
  display: flex;
  transition: 0.4s;
}

nav .menu-item.active,
nav .menu-item:hover {
  background-color: #444;
  border-bottom-color: #FF5858;
}

nav .menu-item a {
  color: inherit;
  text-decoration: none;
}

nav img {
  width: auto;
  height: 60px;
}
</style>
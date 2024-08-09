<template>
  <header class="app-header">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="navbar-collapse">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link class="nav-link" to="/">Home</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/about">About</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/doctor">Doctor</router-link>
          </li>
          <li class="nav-item logo-item">
            <router-link to="/">
              <img src="@/assets/images/logo.png" alt="Logo" class="logo-image">
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/departments">Departments</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/blog">Blog</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/contact">Contact</router-link>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#"><img src="@/assets/images/search-icon.png" alt="Search Icon" class="search-icon"></a>
          </li>
          <li class="nav-item active">
            <router-link class="nav-link" to="/login">LOGIN</router-link>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'AppHeader'
}
</script>

<style scoped>
.app-header {
  width: 100%;
  background-color: #303137;
  color: #fff;
  padding: 1rem 2rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
  height: 150px;
  display: flex;
  align-items: center;
}

 

.navbar {
  width: 100%;
  display: flex;
  justify-content: flex-start;
}

.navbar-collapse {
  display: flex;
  flex: 1;
  align-items: center;
}

.navbar-nav {
  display: flex;
  align-items: center;
  list-style: none;
  padding-left: 0;
  margin-bottom: 0;
  margin-left: 18rem;
}

.nav-item {
  margin: 0 1rem; 
  padding-top: 0.5rem;
}

.logo-item {
  display: flex;
  align-items: center;
}

.logo-image {
  width: 150px; 
  height: auto; 
  position: relative;
  margin-top: 80px;
  top: -18px; 
  z-index: 10;
}


.nav-link {
  color: #fff; 
  text-decoration: none;
  font-size: 18px; 
  margin-top: -10px;
}

.nav-link:hover,
.nav-link:focus {
  color: #0cd268; 
}

.search-icon {
  height: 24px; 
}

.navbar-light .navbar-nav .active>.nav-link,
.navbar-light .navbar-nav .nav-link.active,
.navbar-light .navbar-nav .nav-link.show,
.navbar-light .navbar-nav .show>.nav-link {
  color: #0cd268; 
}
</style>

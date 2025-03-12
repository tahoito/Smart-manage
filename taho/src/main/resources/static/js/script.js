function toggleMenu() {
    let menu = document.getElementById("menu");
    if (menu.style.display === "block") {
        menu.style.display = "none";
    } else {
        menu.style.display = "block";
    }
}

// ✅ グローバルスコープに登録
window.toggleMenu = toggleMenu;

console.log("✅ script.js is loaded and toggleMenu is registered!");

document.addEventListener("click", function (event) {
    let menu = document.getElementById("menu");
    let userIcon = document.getElementById("userIcon");

    if (menu && userIcon && !menu.contains(event.target) && !userIcon.contains(event.target)) {
        menu.style.display = "none";
    }
});

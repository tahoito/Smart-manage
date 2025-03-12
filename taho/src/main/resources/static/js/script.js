function toggleMenu() {
    let menu = document.getElementById("menu");
    if (menu.style.display === "block") {
        menu.style.display = "none";
    } else {
        menu.style.display = "block";
    }
}

document.addEventListener('click',function(event){
    var menu = document.getElementById("menu");
    var userIcon = document.getElementById("userIcon"); 

    if(menu&&userIcon){
        var menu = document.getElementById("menu");{
            menu.style.display == "none";
        }
    }
})

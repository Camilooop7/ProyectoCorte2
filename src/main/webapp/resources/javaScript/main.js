document.addEventListener("DOMContentLoaded", function () {
    const botones = document.querySelectorAll(".btn, .btn-sm, .btn-lg, button, input[type='submit']");

    botones.forEach(btn => {
        btn.addEventListener("click", function () {
            btn.classList.add("active-animation");
            setTimeout(() => {
                btn.classList.remove("active-animation");
            }, 350);
        });
    });

    if (window.location.pathname.includes("index.xhtml")) {
        showToast("🌸 ¡Bienvenida a Biblioteca Artemisa!");
    }
});

function showToast(message) {
    let toast = document.createElement("div");
    toast.innerText = message;
    toast.style.position = "fixed";
    toast.style.bottom = "30px";
    toast.style.right = "30px";
    toast.style.background = "var(--rosa-intenso, #DA395B)";
    toast.style.color = "#fff";
    toast.style.padding = "14px 28px";
    toast.style.borderRadius = "30px";
    toast.style.boxShadow = "0 8px 24px rgba(218,57,91,0.15)";
    toast.style.fontSize = "1.1rem";
    toast.style.zIndex = "9999";
    toast.style.opacity = "0";
    toast.style.transition = "opacity 0.8s";
    document.body.appendChild(toast);

    setTimeout(() => toast.style.opacity = "1", 100);
    setTimeout(() => {
        toast.style.opacity = "0";
        setTimeout(() => toast.remove(), 800);
    }, 3500);
}

document.addEventListener("click", function (e) {
    if (e.target.tagName === "A" && e.target.getAttribute("href") && e.target.getAttribute("href").startsWith("#")) {
        const sectionId = e.target.getAttribute("href").substring(1);
        const section = document.getElementById(sectionId);
        if (section) {
            e.preventDefault();
            section.scrollIntoView({ behavior: "smooth" });
        }
    }
});
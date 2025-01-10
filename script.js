// Smooth scroll to rooms section
function scrollToRooms() {
    document.getElementById('rooms').scrollIntoView({
        behavior: 'smooth'
    });
}

// Simple room slider
let currentPosition = 0;
const roomCards = document.getElementById('roomCards');

setInterval(() => {
    currentPosition = (currentPosition + 1) % 3;
    roomCards.style.transform = `translateX(-${currentPosition * 330}px)`;
}, 3000);

// Header transparency on scroll
window.addEventListener('scroll', () => {
    const header = document.querySelector('header');
    if (window.scrollY > 50) {
        header.style.backgroundColor = 'rgba(0, 0, 0, 0.9)';
    } else {
        header.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
    }
});

const carousel = document.querySelector('.carousel');
const slides = document.querySelectorAll('.slide');
const buttons = document.querySelectorAll('.carousel-btn');
let currentSlide = 0;
const slideCount = slides.length;

function updateCarousel() {
    carousel.style.transform = `translateX(-${currentSlide * 100}%)`;
    buttons.forEach((btn, idx) => {
        btn.classList.toggle('active', idx === currentSlide);
    });
}

function nextSlide() {
    currentSlide = (currentSlide + 1) % slideCount;
    updateCarousel();
}

buttons.forEach((button, idx) => {
    button.addEventListener('click', () => {
        currentSlide = idx;
        updateCarousel();
    });
});

// Auto-advance carousel
setInterval(nextSlide, 5000);
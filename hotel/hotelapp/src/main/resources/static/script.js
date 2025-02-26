// Smooth scroll to rooms section
// function scrollToRooms() {
//     document.getElementById('rooms').scrollIntoView({
//         behavior: 'smooth'
//     });
// }

// Simple room slider
// let currentPosition = 0;
// const roomCards = document.getElementById('roomCards');

// setInterval(() => {
//     currentPosition = (currentPosition + 1) % 3;
//     roomCards.style.transform = `translateX(-${currentPosition * 330}px)`;
// }, 3000);

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

// Mock room availability data
const roomsData = [
    {
        id: 1,
        roomType: 'deluxe',
        name: 'Deluxe Room',
        description: 'Spacious room with city view and premium amenities',
        price: 299,
        maxPeople: 2,
        availableDates: [
            { startDate: '2025-01-12', endDate: '2025-01-15' },
            { startDate: '2025-01-18', endDate: '2025-01-22' }
        ],
        imageUrl: 'images/deluxe.jpg'
    },
    {
        id: 2,
        roomType: 'executive',
        name: 'Executive Suite',
        description: 'Luxury suite with separate living area and ocean view',
        price: 499,
        maxPeople: 4,
        availableDates: [
            { startDate: '2025-01-10', endDate: '2025-01-20' },
            { startDate: '2025-01-25', endDate: '2025-01-30' }
        ],
        imageUrl: 'images/executive.jpg'
    },
    {
        id: 3,
        roomType: 'presidential',
        name: 'Presidential Suite',
        description: 'Ultimate luxury with panoramic views and private butler',
        price: 999,
        maxPeople: 6,
        availableDates: [
            { startDate: '2025-01-14', endDate: '2025-01-18' },
            { startDate: '2025-01-28', endDate: '2025-01-31' }
        ],
        imageUrl: 'images/presidential.jpg'
    }
];

// Function to check if a date range overlaps with the available dates
function isAvailable(room, checkInDate, checkOutDate) {
    if (!checkInDate || !checkOutDate) return true; // Skip if no dates provided

    const checkIn = new Date(checkInDate);
    const checkOut = new Date(checkOutDate);

    return room.availableDates.some(dateRange => {
        const startDate = new Date(dateRange.startDate);
        const endDate = new Date(dateRange.endDate);
        return (checkIn >= startDate && checkOut <= endDate);
    });
}

// Function to filter rooms based on user input
function filterRooms() {
    const checkInDate = document.getElementById("checkInDate").value;
    const checkOutDate = document.getElementById("checkOutDate").value;
    const numberOfPeople = parseInt(document.getElementById("numberOfPeople").value, 10);
    const roomType = document.getElementById("roomType").value;

    const roomCards = document.getElementById("roomCards");
    roomCards.innerHTML = '';  // Clear current room cards

    roomsData.forEach(room => {
        // Filter by room type
        if (roomType !== "all" && room.roomType !== roomType) return;

        // Filter by number of people
        if (numberOfPeople > room.maxPeople) return;

        // Filter by availability
        if (!isAvailable(room, checkInDate, checkOutDate)) return;

        // Create room card dynamically
        const roomCard = document.createElement('div');
        roomCard.classList.add('room-card');
        roomCard.setAttribute('data-room-type', room.roomType);

        roomCard.innerHTML = `
            <img src="${room.imageUrl}" alt="${room.name}">
            <div class="room-card-content">
                <h3>${room.name}</h3>
                <p>${room.description}</p>
                <div class="price">From $${room.price}/night</div>
                <div class="button">
                    <a href="room.html" class="btn">See more</a>
                </div>
            </div>
        `;

        roomCards.appendChild(roomCard);
    });

    if (roomCards.children.length === 0) {
        const noResults = document.createElement('div');
        noResults.classList.add('no-results');
        noResults.innerHTML = `<p>No rooms match your criteria.</p>`;
        roomCards.appendChild(noResults);
    }
}

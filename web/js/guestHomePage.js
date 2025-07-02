document.addEventListener('DOMContentLoaded', () => {
    const track = document.querySelector('.carousel-track');
    const cards = document.querySelectorAll('.book-card');
    const prevBtn = document.querySelector('.prev');
    const nextBtn = document.querySelector('.next');
    const dots = document.querySelectorAll('.dot');

    const visible = 4; // số lượng sách hiển thị mỗi lượt
    const totalSlides = Math.ceil(cards.length / visible);
    let index = 0;

    function update() {
        const width = 250; // test cố định, nếu HTML mỗi book-card là 25% width
        track.style.transform = `translateX(-${width * visible * index}px)`;

        dots.forEach((dot, i) => {
            dot.classList.toggle('active', i === index);
        });
    }

    prevBtn.onclick = () => {
        if (index > 0) {
            index--;
            update();
        }
    };

    nextBtn.onclick = () => {
        if (index < totalSlides - 1) {
            index++;
            update();
        }
    };

    dots.forEach((dot, i) => {
        dot.onclick = () => {
            index = i;
            update();
        };
    });

    update();
});

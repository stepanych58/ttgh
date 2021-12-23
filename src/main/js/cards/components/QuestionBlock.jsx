import React from 'react';
import './sort.css';


function QuestionBlock({ outline, items, icons }) {
    const [visibleQuestion, setVisibleQuestion] = React.useState(false);
    const sortRef = React.useRef();

    const teggleVisibleQuestion = () => {
        setVisibleQuestion(!visibleQuestion)
    };


    const handleOutsideClickQuestion = (e) => {
        if (!e.path.includes(sortRef.current)) {
            setVisibleQuestion(false);
        }
    };

    React.useEffect(() => {
        document.body.addEventListener('click', handleOutsideClickQuestion);
    }, []);


    return (
        <>
            <div className='question__block' ref={sortRef}>
                <span onClick={teggleVisibleQuestion}>
                    {icons.map((icon) =>
                        <span className={`icon__svg ${outline ? 'icon__svg--outline' : ''}`}>{icon}</span>
                    )}
                </span>
            </div>

            {visibleQuestion && <div className='sortpopup__wrapper-quest'>
                <div>
                    {items.map((text, index) => (
                        <p key={`${index}`}> {text}</p>
                    ))}
                </div>
            </div>}
        </>
    );
};


export default QuestionBlock;
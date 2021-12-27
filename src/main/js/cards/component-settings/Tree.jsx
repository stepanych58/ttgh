import React from "react";
import './settings.css'

const Tree = props => {
    let ref = React.useRef()

    const [state, setState] = React.useState({
        isScrolling: false,
        clientX: 0,
        scrollX: 0,
    })

    React.useEffect(() => {
        const el = ref.current
        if (el) {
            const onWheel = e => {
                e.preventDefault()
                el.scrollTo({
                    left: el.scrollLeft + e.deltaY * 4,
                    behavior: 'smooth'
                })
            }
            el.addEventListener('wheel', onWheel)

            return () => el.removeEventListener('wheel', onWheel)
        }
    }, [])

    const onMouseDown = e => {
        if (ref && ref.current && !ref.current.contains(e.terget)) {
            return
        }
        e.preventDefault()
        setState({
            ...state,
            isScrolling: true,
            clientX: e.clientX
        })
    }
    const onMouseUp = e => {
        if (ref && ref.current && !ref.current.contains(e.terget)) {
            return
        }
        e.preventDefault()
        setState({
            ...state,
            isScrolling: false
        })


    }
    const onMouseMove = e => {
        if (ref && ref.current && !ref.current.contains(e.terget)) {
            return
        }
        e.preventDefault()

        const { clientX, scrollX, isScrolling } = state
        if (isScrolling) {
            ref.current.scrollLeft = scrollX + e.clientX - clientX

            setState({
                ...state,
                scrollX: scrollX + e.clientX - clientX,
                clientX: e.clientX
            })
        }
    }



    React.useEffect(() => {
        document.addEventListener('mousedown', onMouseDown)
        document.addEventListener('mouseup', onMouseUp)
        document.addEventListener('mousemove', onMouseMove)

        return () => {
            document.removeEventListener('mousedown', onMouseDown)
            document.removeEventListener('mouseup', onMouseUp)
            document.removeEventListener('mousemove', onMouseMove)
        }
    })
    return (
        <div
            ref={ref}
            className={props._class}
            onMouseDown={onMouseDown}
            onMouseUp={onMouseUp}
            onMouseMove={onMouseMove}
        >
            {
                props.children
            }
        </div>
    );
}

export default Tree;
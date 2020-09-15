import React from 'react';
import Footer from './Footer';
import { shallow, mount } from 'enzyme';
import expect from 'expect';

test('matts name exists in footer', () => {
    const wrapper = shallow(<Footer />);
    expect(wrapper.text().includes('Matt Meskell')).toBe(true);
  });



